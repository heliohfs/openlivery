package com.openlivery.service.product.service

import com.openlivery.service.common.domain.entity.Customer
import com.openlivery.service.common.repository.AppliedDiscountRepository
import com.openlivery.service.product.domain.entity.Cart
import com.openlivery.service.product.domain.entity.DeliveryFeeDiscount
import com.openlivery.service.product.domain.entity.OrderDiscount
import com.openlivery.service.product.domain.entity.ProductDiscount
import com.openlivery.service.product.domain.enums.DiscountType
import com.openlivery.service.product.repository.CartRepository
import com.openlivery.service.product.repository.CatalogProductRepository
import com.openlivery.service.product.repository.CouponRepository
import com.openlivery.service.product.repository.DiscountRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Service
@Transactional
class CartService(
        private val cartRepository: CartRepository,
        private val couponRepository: CouponRepository,
        private val discountRepository: DiscountRepository,
        private val catalogProductRepository: CatalogProductRepository,
        private val appliedDiscountRepository: AppliedDiscountRepository
) {

    fun getCart(cartId: String): Cart {
        var cart = cartRepository.findById(cartId)
                .orElseGet { Cart(cartId) }

        cart.couponApplied?.let { code ->
            val coupon = couponRepository.findById(code)
            if (coupon.isEmpty) cart.couponApplied = null
            else coupon.get().discounts.forEach { discount ->
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
                        cart = cartRepository.save(cart)
                    }
        }

        cart.finalOrderValue = if (cart.orderDiscount != null)
            if (cart.orderDiscountType == DiscountType.PERCENT_OFF)
                cart.orderValue.subtract(cart.orderValue.multiply(cart.orderDiscount))
            else cart.orderValue.subtract(cart.orderDiscount)
        else cart.orderValue

        cart.finalDeliveryFee = if (cart.deliveryFeeDiscount != null)
            if (cart.deliveryFeeDiscountType == DiscountType.PERCENT_OFF)
                cart.deliveryFee?.subtract((cart.deliveryFee ?: BigDecimal.ZERO).multiply(cart.deliveryFeeDiscount
                        ?: BigDecimal.ZERO))
            else cart.deliveryFee?.subtract(cart.deliveryFeeDiscount ?: BigDecimal.ZERO)
        else cart.deliveryFee

        cart.finalValue = cart.finalOrderValue.add(cart.finalDeliveryFee ?: BigDecimal.ZERO)

        return cart
    }

}