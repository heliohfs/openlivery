package com.openlivery.service.product.domain.model

import com.openlivery.service.product.domain.entity.CartProduct
import com.openlivery.service.product.domain.enums.DiscountAccess
import com.openlivery.service.product.domain.enums.DiscountType
import java.math.BigDecimal

class CartProductModel private constructor(
        val id: Long,
        val basePrice: BigDecimal,
        val finalPrice: BigDecimal,
        val amount: Int,
        val discountApplied: Boolean,
        val discountSource: DiscountAccess?,
        val discountType: DiscountType?,
        val discount: BigDecimal?,
        val pictureStorageKey: String?
) {

    companion object {
        fun from(cartProduct: CartProduct): CartProductModel {
            return CartProductModel(
                    id = cartProduct.id,
                    basePrice = cartProduct.basePrice,
                    finalPrice = cartProduct.finalPrice,
                    amount = cartProduct.amount,
                    discountApplied = cartProduct.discountApplied,
                    discountSource = cartProduct.discountSource,
                    discountType = cartProduct.discountType,
                    discount = cartProduct.discount,
                    pictureStorageKey = cartProduct.pictureStorageKey
            )
        }
    }

}