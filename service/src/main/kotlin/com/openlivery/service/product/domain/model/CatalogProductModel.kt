package com.openlivery.service.product.domain.model

import com.openlivery.service.product.domain.entity.CatalogProduct
import com.openlivery.service.product.domain.enums.DiscountType
import java.math.BigDecimal

class CatalogProductModel private constructor(
        val id: Long,
        val name: String,
        val basePrice: BigDecimal,
        val finalPrice: BigDecimal,
        val itemCode: String?,
        val description: String?,
        val pictureStorageKey: String?,
        val discountType: DiscountType?,
        val discount: BigDecimal?,
        val discountApplied: Boolean
) {

    companion object {
        fun from(catalogProduct: CatalogProduct): CatalogProductModel {
            return CatalogProductModel(
                    id = catalogProduct.base.id,
                    name = catalogProduct.name,
                    basePrice = catalogProduct.basePrice,
                    finalPrice = catalogProduct.finalPrice,
                    itemCode = catalogProduct.itemCode,
                    description = catalogProduct.description,
                    pictureStorageKey = catalogProduct.pictureStorageKey,
                    discountType = catalogProduct.discountType,
                    discount = catalogProduct.discount,
                    discountApplied = catalogProduct.discountApplied
            )
        }
    }

}