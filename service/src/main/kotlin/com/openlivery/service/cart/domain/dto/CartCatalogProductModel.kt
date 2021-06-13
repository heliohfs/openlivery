package com.openlivery.service.cart.domain.dto

import com.openlivery.service.cart.domain.CartCatalogProduct
import java.math.BigDecimal

class CartCatalogProductModel(
        val id: Long,
        val finalPrice: BigDecimal,
        val basePrice: BigDecimal,
        val pictureStorageKey: String? = null,
        val amount: Int,
) {
    companion object {
        fun from(product: CartCatalogProduct): CartCatalogProductModel = CartCatalogProductModel(
                id = product.id,
                finalPrice = product.finalPrice,
                basePrice = product.basePrice,
                pictureStorageKey = product.pictureStorageKey,
                amount = product.amount
        )
    }
}