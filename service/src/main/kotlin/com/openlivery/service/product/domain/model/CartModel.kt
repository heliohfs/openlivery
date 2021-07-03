package com.openlivery.service.product.domain.model

import com.openlivery.service.product.domain.entity.Cart
import com.openlivery.service.product.domain.entity.CartDeliveryAddress
import com.openlivery.service.product.domain.enums.DiscountAccess
import com.openlivery.service.product.domain.enums.DiscountType
import java.math.BigDecimal

class CartModel private constructor(
        val couponApplied: String?,
        val orderDiscountSource: String?,
        val orderDiscountType: DiscountType?,
        val orderDiscount: BigDecimal?,
        val orderValue: BigDecimal,
        val orderValueSaved: BigDecimal?,
        val finalOrderValue: BigDecimal,
        val orderDiscountApplied: Boolean,
        val deliveryFeeDiscountSource: String?,
        val deliveryFeeDiscountType: DiscountType?,
        val deliveryFeeDiscount: BigDecimal?,
        val deliveryFee: BigDecimal?,
        val finalDeliveryFee: BigDecimal?,
        val deliveryFeeDiscountApplied: Boolean,
        val products: List<CartProductModel>,
        val deliveryAddress: CartDeliveryAddress?,
        val finalValue: BigDecimal,
        val orderingAvailable: Boolean
) {

    companion object {
        fun from(cart: Cart): CartModel {
            return CartModel(
                    couponApplied = cart.couponApplied,
                    orderDiscountSource = cart.orderDiscountSource,
                    orderDiscountType = cart.orderDiscountType,
                    orderDiscount = cart.orderDiscount,
                    orderValueSaved = cart.orderValueSaved,
                    orderValue = cart.orderValue,
                    finalOrderValue = cart.finalOrderValue,
                    orderDiscountApplied = cart.orderDiscountApplied,
                    deliveryFeeDiscountSource = cart.deliveryFeeDiscountSource,
                    deliveryFeeDiscountType = cart.deliveryFeeDiscountType,
                    deliveryFeeDiscount = cart.deliveryFeeDiscount,
                    deliveryFee = cart.deliveryFee,
                    finalDeliveryFee = cart.finalDeliveryFee,
                    deliveryFeeDiscountApplied = cart.deliveryFeeDiscountApplied,
                    products = cart.products.map { CartProductModel.from(it) },
                    deliveryAddress = cart.deliveryAddress,
                    finalValue = cart.finalValue,
                    orderingAvailable = cart.orderingAvailable
            )
        }
    }

}