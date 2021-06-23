package com.openlivery.service.product.domain.model

import com.openlivery.service.product.domain.entity.CatalogProduct
import java.math.BigDecimal

class CatalogProductModel private constructor(
        val id: Long,
        val name: String,
        val basePrice: BigDecimal,
        val finalPrice: BigDecimal,
        val discountApplied: Boolean,
        val itemCode: String?,
        val description: String?,
        val pictureStorageKey: String?,
        val decimalDiscount: BigDecimal?
) {

    companion object {
        fun from(catalogProduct: CatalogProduct): CatalogProductModel {
            return CatalogProductModel(
                    id = catalogProduct.id,
                    name = catalogProduct.name,
                    basePrice = catalogProduct.basePrice,
                    finalPrice = catalogProduct.finalPrice,
                    discountApplied = catalogProduct.discountApplied,
                    itemCode = catalogProduct.itemCode,
                    description = catalogProduct.description,
                    pictureStorageKey = catalogProduct.pictureStorageKey,
                    decimalDiscount = catalogProduct.decimalDiscount
            )
        }
    }

}