package com.openlivery.service.order.service

import com.openlivery.service.order.domain.Cart
import com.openlivery.service.order.domain.dto.OrderCartModel
import com.openlivery.service.order.domain.dto.OrderCartProductModel
import com.openlivery.service.order.domain.enums.DiscountType
import com.openlivery.service.order.repository.CartRepository
import org.apache.commons.lang3.tuple.MutablePair
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.*

@Service("OrderCartService")
class CartService(
        private val catalogProductService: CatalogProductService,
        private val couponService: CouponService,
        private val repository: CartRepository
) {

    //TODO: optimize to decrease response time
    fun getCart(cartId: String): OrderCartModel {
        val cart = repository.findById(cartId)
                .orElse(Cart(cartId))

        val coupon = cart.appliedCoupon?.let { code ->
            couponService.findByCode(code)
                    .orElseThrow { error("Coupon does not exist or is not active") }
        }

        val orderDecimalDiscount = coupon?.discounts?.maxByOrNull { discount ->
            if (discount.discountType === DiscountType.ORDER) discount.decimalDiscount
            else BigDecimal.ZERO
        }?.decimalDiscount ?: BigDecimal.ZERO

        val deliveryFeeDecimalDiscount = coupon?.discounts?.maxByOrNull { discount ->
            if (discount.discountType === DiscountType.DELIVERY_FEE) discount.decimalDiscount
            else BigDecimal.ZERO
        }?.decimalDiscount ?: BigDecimal.ZERO

        val (products, totalValue) = cart.products.fold(MutablePair(mutableSetOf<OrderCartProductModel>(), BigDecimal.ZERO))
        { acc, cartProduct ->
            val catalogProduct = catalogProductService.findById(cartProduct.id)
                    .orElseThrow { error("Product not found") }

            val decimalDiscount = if (!catalogProduct.discountApplied) {
                coupon?.discounts?.find { it.product?.id == catalogProduct.id }?.decimalDiscount ?: BigDecimal.ZERO
            } else BigDecimal.ZERO

            val productModel = OrderCartProductModel(
                    id = catalogProduct.id,
                    finalPrice = catalogProduct.finalPrice.subtract(catalogProduct.finalPrice.multiply(decimalDiscount)),
                    basePrice = catalogProduct.basePrice,
                    pictureStorageKey = catalogProduct.pictureStorageKey,
                    amount = cartProduct.amount,
                    discountApplied = catalogProduct.discountApplied
            )

            val price = catalogProduct.finalPrice.multiply(BigDecimal(cartProduct.amount))

            acc.left.add(productModel)
            acc.right = acc.right.add(price.subtract(price.multiply(decimalDiscount)))
            acc
        }

        return OrderCartModel(
                totalValue = totalValue.subtract(totalValue.multiply(orderDecimalDiscount)),
                deliveryFee = BigDecimal.ZERO.subtract(BigDecimal.ZERO.multiply(deliveryFeeDecimalDiscount)),
                products = products,
                couponApplied = cart.appliedCoupon
        )
    }

    fun addProduct(cartId: String, productId: Long, amount: Int): Cart {
        if (amount < 0) throw error("Product amount must be greater than 0.")

        val productExists = catalogProductService.existsById(productId)
        if (!productExists)
            throw error("Product does not exist.")

        return repository.findById(cartId)
                .orElseGet { Cart(cartId) }
                .apply {
                    products.find { it.id == productId }
                            .apply {
                                if (this != null) this.amount += amount
                                else products.add(Cart.Product(productId, amount))
                            }
                }
                .run { repository.save(this) }
    }

    fun removeProduct(cartId: String, productId: Long) = repository.findById(cartId)
            .orElse(Cart(cartId))
            .apply { this.products.removeAll { it.id == productId } }
            .run { repository.save(this) }

    fun increaseProductAmount(cartId: String, productId: Long): Cart {
        val cart = repository.findById(cartId)
                .orElseThrow { error("Product not in cart") }

        return Optional.ofNullable(cart.products.find { product -> product.id == productId })
                .orElseThrow { error("Product not in cart") }
                .apply { amount += 1 }
                .run { repository.save(cart) }
    }

    fun decreaseProductAmount(cartId: String, productId: Long): Cart {
        val cart = repository.findById(cartId)
                .orElseThrow { error("Product not in cart") }

        return Optional.ofNullable(cart.products.find { product -> product.id == productId })
                .orElseThrow { error("Product not in cart") }
                .apply { amount -= if (amount > 0) 1 else 0 }
                .run { repository.save(cart) }
    }

    fun applyCoupon(isAnonymous: Boolean, cartId: String, couponCode: String): Cart {
        val cart = repository.findById(cartId)
                .orElse(Cart(cartId))

        couponService.findByCode(couponCode)
                .orElseThrow { error("Coupon does not exist or is not active.") }
                .run { authRequired && isAnonymous && throw error("Coupon not applicable by unauthenticated users.") }

        cart.appliedCoupon = couponCode
        return repository.save(cart)
    }

    fun removeCoupon(cartId: String): Cart {
        val cart = repository.findById(cartId)
                .orElse(Cart(cartId))

        cart.appliedCoupon ?: throw error("No coupon applied")
        cart.appliedCoupon = null
        return repository.save(cart)
    }

    fun clearCart(cartId: String) {
        repository.deleteById(cartId)
    }

}