package com.openlivery.service.product.domain.model

import com.openlivery.service.product.domain.entity.CartProduct
import com.openlivery.service.product.domain.enums.DiscountType
import java.math.BigDecimal

class CartProductModel private constructor(
        val id: Long,
        val basePrice: BigDecimal,
        val finalPrice: BigDecimal,
        val amount: Int,
        val discountApplied: Boolean,
        val discountSource: String?,
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
                    discountApplied = cartProduct.discount != null,
                    discountSource = cartProduct.discount?.campaign?.description,
                    discountType = cartProduct.discount?.kind,
                    discount = cartProduct.discount?.discount,
                    pictureStorageKey = cartProduct.pictureStorageKey
            )
        }
    }

}