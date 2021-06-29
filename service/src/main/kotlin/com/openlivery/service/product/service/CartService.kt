package com.openlivery.service.product.service

import com.openlivery.service.common.domain.model.AddressInput
import com.openlivery.service.common.repository.AddressRepository
import com.openlivery.service.common.repository.AppliedDiscountRepository
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
        private val cartRepository: CartRepository,
        private val couponRepository: CouponRepository,
        private val discountRepository: DiscountRepository,
        private val catalogProductRepository: CatalogProductRepository,
        private val appliedDiscountRepository: AppliedDiscountRepository,
        private val addressRepository: AddressRepository
) {

    fun findCart(cartId: String) = cartRepository.findById(cartId)
            .orElseGet { Cart(cartId) }

    fun getTransientCart(cartId: String) = cartRepository.findById(cartId)
            .orElseGet { Cart(cartId) }
            .let { transitCart(it) }

    fun addProductToCart(cartId: String, productId: Long, amount: Int) =
            catalogProductRepository.findById(productId)
                    .map { CartProduct(it.base.id, amount) }
                    .orElseThrow { error("") }
                    .let { Pair(findCart(cartId), it) }
                    .also { (cart) -> cart.products.removeIf { it.id == productId } }
                    .also { (cart, product) -> cart.products.add(product) }.first
                    .run { cartRepository.save(this) }
                    .let { transitCart(it) }


    fun removeProductFromCart(cartId: String, productId: Long) = findCart(cartId)
            .apply { products.removeIf { it.id == productId } }
            .run { cartRepository.save(this) }
            .let { transitCart(it) }

    fun increaseCartProductAmount(cartId: String, productId: Long, amount: Int) = findCart(cartId)
            .let { Pair(it, it.products.find { product -> product.id == productId }) }
            .also { (_, product) -> product?.amount?.plus(1) }.first
            .run { cartRepository.save(this) }
            .let { transitCart(it) }

    fun decreaseCartProductAmount(cartId: String, productId: Long, amount: Int) = findCart(cartId)
            .let { Pair(it, it.products.find { product -> product.id == productId }) }
            .also { (_, product) -> max(0, product?.amount?.minus(amount) ?: 0) }
            .also { (cart) -> cart.products.removeIf { it.amount <= 0 } }.first
            .run { cartRepository.save(this) }
            .let { transitCart(it) }

    fun setCartExistingDeliveryAddress(cartId: String, addressId: Long) = addressRepository.findById(addressId)
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
            .let { transitCart(it) }

    fun setCartNewDeliveryAddress(cartId: String, addressInput: AddressInput) =
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
                    .let { transitCart(it) }

    fun applyCouponToCart(cartId: String, couponCode: String, isAnonymous: Boolean) =
            couponRepository.findAvailableById(couponCode, isAnonymous)
                    .orElseThrow { error("") }
                    .let { Pair(findCart(cartId), it) }
                    .also { (cart) -> cart.couponApplied = couponCode }.first
                    .run(cartRepository::save)
                    .let(this::transitCart)

    fun removeCouponFromCart(cartId: String) = findCart(cartId)
            .apply { couponApplied = null }
            .run(cartRepository::save)
            .let(this::transitCart)

    fun clearCart(cartId: String) = findCart(cartId)
            .apply { products = hashSetOf() }
            .apply { couponApplied = null }
            .run(cartRepository::save)
            .let(this::transitCart)

    fun transitCart(cart: Cart): Cart {
        cart.couponApplied?.let { code ->
            couponRepository.findById(code)
                    .ifPresentOrElse({ coupon ->
                        coupon.discounts.forEach { discount ->
                            when (discount) {
                                is OrderDiscount -> cart.apply {
                                    orderDiscount = discount.discount
                                    orderDiscountApplied = true
                                    orderDiscountSource = discount.accessBy
                                    orderDiscountType = discount.discountType
                                }
                                is DeliveryFeeDiscount -> cart.apply {
                                    deliveryFeeDiscount = discount.discount
                                    deliveryFeeDiscountApplied = true
                                    deliveryFeeDiscountSource = discount.accessBy
                                    deliveryFeeDiscountType = discount.discountType
                                }
                                else -> cart.products.forEach {
                                    if (it.id == (discount as ProductDiscount).product.id) {
                                        it.discount = discount.discount
                                        it.discountApplied = true
                                        it.discountSource = discount.accessBy
                                        it.discountType = discount.discountType
                                    }
                                }
                            }
                        }
                    }, { cart.couponApplied = null })
        }

        cart.products.forEach { cartProduct ->
            catalogProductRepository.findById(cartProduct.id)
                    .ifPresentOrElse({ product ->
                        when (cartProduct.discountType) {
                            DiscountType.PERCENT_OFF ->
                                cartProduct.finalPrice = product.finalPrice.subtract(product.finalPrice.multiply(cartProduct.discount))
                            DiscountType.AMOUNT_OFF ->
                                cartProduct.finalPrice = product.finalPrice.subtract(cartProduct.discount)
                            else ->
                                cartProduct.finalPrice = product.finalPrice
                        }
                        cartProduct.basePrice = product.basePrice
                        cartProduct.pictureStorageKey = product.pictureStorageKey
                        cart.orderValue = cart.orderValue.add(cartProduct.finalPrice.multiply(BigDecimal(cartProduct.amount)))
                    }) {
                        cart.products.removeIf { product -> product.id == cartProduct.id }
                        cartRepository.save(cart)
                    }
        }

        cart.finalOrderValue = cart.orderDiscount?.let { discount ->
            if (cart.orderDiscountType == DiscountType.PERCENT_OFF)
                cart.orderValue.subtract(cart.orderValue.multiply(discount))
            else cart.orderValue.subtract(cart.orderDiscount)
        } ?: cart.orderValue

        cart.finalDeliveryFee = cart.deliveryFeeDiscount?.let { discount ->
            if (cart.deliveryFeeDiscountType == DiscountType.PERCENT_OFF)
                cart.deliveryFee?.subtract((cart.deliveryFee ?: BigDecimal.ZERO).multiply(discount))
            else cart.deliveryFee?.subtract(cart.deliveryFeeDiscount ?: BigDecimal.ZERO)
        } ?: cart.deliveryFee

        cart.finalValue = cart.finalOrderValue.add(cart.finalDeliveryFee ?: BigDecimal.ZERO)

        return cart
    }

}