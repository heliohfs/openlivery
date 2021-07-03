package com.openlivery.service.product.service

import com.openlivery.service.common.domain.entity.Customer
import com.openlivery.service.common.domain.model.AddressInput
import com.openlivery.service.common.repository.AddressRepository
import com.openlivery.service.common.system.SystemParameters
import com.openlivery.service.product.domain.entity.*
import com.openlivery.service.product.domain.enums.DiscountType
import com.openlivery.service.product.repository.CartRepository
import com.openlivery.service.product.repository.CatalogProductRepository
import com.openlivery.service.product.repository.CouponRepository
import com.openlivery.service.product.repository.DiscountRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import kotlin.math.max

@Service
@Transactional
class CartService(
        private val systemParameters: SystemParameters,
        private val cartRepository: CartRepository,
        private val couponRepository: CouponRepository,
        private val discountRepository: DiscountRepository,
        private val catalogProductRepository: CatalogProductRepository,
        private val addressRepository: AddressRepository,
        private val discountService: DiscountService
) {

    fun findCart(cartId: String): Cart = cartRepository.findById(cartId)
            .orElseGet { Cart(cartId) }

    fun getTransientCart(cartId: String, customer: Customer?): Cart =
            cartRepository.findById(cartId)
                    .orElseGet { Cart(cartId) }
                    .let { transitCart(it, customer) }

    fun addProductToCart(cartId: String, productId: Long, amount: Int): Cart = catalogProductRepository
            .findById(productId)
            .map { CartProduct(it.base.id, amount) }
            .orElseThrow { error("") }
            .let { Pair(findCart(cartId), it) }
            .also { (cart) -> cart.products.removeIf { it.id == productId } }
            .also { (cart, product) -> cart.products.add(product) }.first
            .run { cartRepository.save(this) }

    fun removeProductFromCart(cartId: String, productId: Long): Cart = findCart(cartId)
            .apply { products.removeIf { it.id == productId } }
            .run { cartRepository.save(this) }

    fun increaseCartProductAmount(cartId: String, productId: Long, amount: Int): Cart = findCart(cartId)
            .let { Pair(it, it.products.find { product -> product.id == productId }) }
            .also { (_, product) -> product?.amount?.plus(1) }.first
            .run { cartRepository.save(this) }

    fun decreaseCartProductAmount(cartId: String, productId: Long, amount: Int): Cart = findCart(cartId)
            .let { Pair(it, it.products.find { product -> product.id == productId }) }
            .also { (_, product) -> max(0, product?.amount?.minus(amount) ?: 0) }
            .also { (cart) -> cart.products.removeIf { it.amount <= 0 } }.first
            .run { cartRepository.save(this) }

    fun setCartExistingDeliveryAddress(cartId: String, addressId: Long): Cart = addressRepository
            .findById(addressId)
            .map {
                CartDeliveryAddress(
                        streetNumber = it.streetNumber,
                        streetName = it.streetName,
                        cityName = it.cityName,
                        governingDistrict = it.governingDistrict,
                        country = it.country,
                        additionalInfo = it.additionalInfo,
                        latitude = it.latitude,
                        longitude = it.longitude
                )
            }
            .orElseThrow { error("") }
            .let { Pair(findCart(cartId), it) }
            .also { (cart, address) -> cart.deliveryAddress = address }.first
            .run { cartRepository.save(this) }

    fun setCartNewDeliveryAddress(cartId: String, addressInput: AddressInput): Cart =
            Pair(findCart(cartId), CartDeliveryAddress(
                    streetNumber = addressInput.streetNumber,
                    streetName = addressInput.streetName,
                    cityName = addressInput.cityName,
                    governingDistrict = addressInput.governingDistrict,
                    country = addressInput.country,
                    additionalInfo = addressInput.additionalInfo,
                    latitude = addressInput.latitude,
                    longitude = addressInput.longitude
            ))
                    .also { (cart, address) -> cart.deliveryAddress = address }.first
                    .run { cartRepository.save(this) }

    fun applyCouponToCart(cartId: String, couponCode: String, isAnonymous: Boolean) =
            couponRepository.findByCodeAndActiveIsTrue(couponCode)
                    .orElseThrow { error("") }
                    .let { Pair(findCart(cartId), it) }
                    .also { (cart) -> cart.couponApplied = couponCode }.first
                    .run(cartRepository::save)

    fun removeCouponFromCart(cartId: String): Cart = findCart(cartId)
            .apply { couponApplied = null }
            .run(cartRepository::save)

    fun clearCart(cartId: String): Cart = findCart(cartId)
            .apply { products = hashSetOf() }
            .apply { couponApplied = null }
            .run(cartRepository::save)

    //TODO: optimize
    fun transitCart(cart: Cart, customer: Customer?): Cart {
        cart.products.forEach { cartProduct ->
            catalogProductRepository.findById(cartProduct.id).ifPresentOrElse({
                cartProduct.apply {
                    discountType = it.discountType
                    discount = it.discount
                    discountId = it.discountId
                    discountApplied = true
                    finalPrice = it.finalPrice
                    basePrice = it.basePrice
                    discountSource = it.discountSource
                    pictureStorageKey = it.pictureStorageKey
                }
                cart.orderValue = cart.orderValue.add(cartProduct.finalPrice.multiply(BigDecimal(cartProduct.amount)))
            }, {
                cart.products.removeIf { product -> product.id == cartProduct.id }
                cartRepository.save(cart)
            })
        }

        cart.finalOrderValue = cart.orderValue
        cart.finalDeliveryFee = cart.deliveryFee

        val couponDiscounts = cart.couponApplied?.let {
            discountService.findMatchingDiscountsByCouponCode(
                    couponCode = it,
                    orderValue = cart.orderValue,
                    deliveryFee = cart.deliveryFee,
                    customer = customer
            ).ifEmpty {
                cart.couponApplied = null
                cartRepository.save(cart)
                throw error("Invalid coupon")
            }
        } ?: listOf()

        val underlyingDiscounts = discountService.findMatchingUnderlyingDiscounts(
                customer = customer,
                orderValue = cart.orderValue,
                deliveryFee = cart.deliveryFee
        )

        val discounts = underlyingDiscounts.union(couponDiscounts)

        discounts.filter { it.isProductDiscount }.forEach { discount ->
            cart.products.find { it.id == discount.productId }
                    ?.apply {
                        val finalValue = if (discount.discountType == DiscountType.AMOUNT_OFF) basePrice.subtract(discount.discount)
                        else basePrice.subtract(basePrice.multiply(discount.discount))

                        if (finalValue.compareTo(finalPrice) == -1) {
                            val difference = finalPrice.subtract(finalValue).multiply(BigDecimal(amount))
                            cart.orderValue = cart.orderValue.subtract(difference)
                            cart.finalOrderValue = cart.finalOrderValue.subtract(difference)

                            this.discount = discount.discount
                            discountApplied = true
                            discountId = discount.id
                            discountType = discount.discountType
                            discountSource = discount.campaign.description
                            finalPrice = finalValue
                        }
                    }
        }

        discounts.filter { !it.isProductDiscount }.forEach { discount ->
            if (discount.isOrderDiscount) {
                cart.apply {
                    val saved = if (discount.discountType === DiscountType.AMOUNT_OFF) discount.discount
                    else discount.maxOrderDiscountValue?.let {
                        val discountAmount = orderValue.multiply(discount.discount)
                        if (discountAmount.compareTo(it) == -1) discountAmount
                        else discount.maxOrderDiscountValue
                    } ?: orderValue.multiply(discount.discount)

                    val finalValue = orderValue.subtract(saved)

                    if (finalValue.compareTo(finalOrderValue) == -1) {
                        orderDiscount = discount.discount
                        orderDiscountApplied = true
                        orderDiscountId = discount.id
                        orderDiscountType = discount.discountType
                        orderValueSaved = saved
                        orderDiscountSource = discount.campaign.description
                        finalOrderValue = finalValue
                    }
                }
            } else {
                cart.apply {
                    deliveryFee?.let { deliveryFee ->
                        val finalValue = if (discount.discountType == DiscountType.AMOUNT_OFF) deliveryFee.subtract(discount.discount)
                        else deliveryFee.subtract(deliveryFee.multiply(discount.discount))

                        if (finalValue.compareTo(finalOrderValue) == -1) {
                            deliveryFeeDiscount = discount.discount
                            deliveryFeeDiscountApplied = true
                            deliveryFeeDiscountId = discount.id
                            deliveryFeeDiscountType = discount.discountType
                            deliveryFeeDiscountSource = discount.campaign.description
                            finalDeliveryFee = finalValue
                        }
                    }
                }
            }
        }

        cart.finalValue = cart.finalOrderValue.add(cart.finalDeliveryFee ?: BigDecimal.ZERO)

        val parameters = systemParameters.getParameters()

        cart.orderingAvailable = (parameters.minOrderValue.compareTo(cart.finalOrderValue) <= 0) &&
                cart.deliveryAddress != null &&
                cart.deliveryFee != null


        return cart
    }

}