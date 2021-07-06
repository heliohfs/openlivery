package com.openlivery.service.product.service

import com.openlivery.service.common.domain.entity.Customer
import com.openlivery.service.common.domain.model.AddressInput
import com.openlivery.service.common.repository.AddressRepository
import com.openlivery.service.common.system.SystemParameters
import com.openlivery.service.product.domain.entity.*
import com.openlivery.service.product.repository.CartRepository
import com.openlivery.service.product.repository.CouponRepository
import com.openlivery.service.product.repository.DiscountRepository
import com.openlivery.service.product.repository.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.math.max

@Service
@Transactional
class CartService(
        private val productRepository: ProductRepository,
        private val systemParameters: SystemParameters,
        private val cartRepository: CartRepository,
        private val couponRepository: CouponRepository,
        private val addressRepository: AddressRepository,
        private val discountRepository: DiscountRepository,
) {

    fun findCart(cartId: String): Cart = cartRepository.findById(cartId)
            .orElseGet { Cart(cartId) }

    fun getTransientCart(cartId: String, customer: Customer?): Cart =
            cartRepository.findById(cartId)
                    .orElseGet { Cart(cartId) }
                    .let { transitCart(it, customer) }

    fun addProductToCart(cartId: String, productId: Long, amount: Int): Cart = productRepository
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

    fun transitCart(cart: Cart, customer: Customer?): Cart {
        val products = cart.products.mapNotNullTo(hashSetOf()) { cartProduct ->
            productRepository.findById(cartProduct.id).map { product ->
                cartProduct.apply {
                    basePrice = product.basePrice
                    pictureStorageKey = product.pictureStorageKey
                }
                product
            }.orElseGet {
                cart.products.removeIf { product -> product.id == cartProduct.id }
                cartRepository.save(cart)
                null
            }
        }

        // Tries to apply any available discount to order and delivery fee
        discountRepository.findAllAvailableDiscounts(
                customer = customer,
                orderValue = cart.orderValue,
                deliveryFee = cart.deliveryFee,
                couponCode = cart.couponApplied
        ).forEach { discount ->
            val validProducts = when (discount) {
                is OrderDiscount -> discount.validProducts
                is DeliveryFeeDiscount -> discount.validProducts
                else -> null
            }

            if (validProducts == null || validProducts.isEmpty() ||
                    (validProducts.containsAll(products) && products.containsAll(validProducts))) {
                when (discount) {
                    is OrderDiscount -> cart.orderDiscount = discount
                    is DeliveryFeeDiscount -> cart.deliveryFeeDiscount = discount
                    is ProductDiscount -> cart.products.find { cartProduct -> cartProduct.id == discount.product.id }?.apply {
                        this.discount = discount
                    }
                }
            }
        }

        val parameters = systemParameters.getParameters()

        cart.orderingAvailable = (parameters.minOrderValue.compareTo(cart.finalOrderValue) <= 0) &&
                cart.finalValue != null &&
                cart.deliveryAddress != null &&
                cart.deliveryFee != null

        return cart
    }

}