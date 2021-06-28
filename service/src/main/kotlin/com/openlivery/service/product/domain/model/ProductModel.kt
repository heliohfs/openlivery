package com.openlivery.service.product.domain.model

import com.openlivery.service.product.domain.entity.Product
import java.math.BigDecimal
import java.time.LocalDateTime

class ProductModel private constructor(
        val id: Long,
        val active: Boolean,
        val createdDateTime: LocalDateTime,
        val changedDateTime: LocalDateTime,
        val version: Int,
        val name: String,
        val basePrice: BigDecimal,
        val description: String? = null,
        val itemCode: String? = null,
        val pictureStorageKey: String? = null
) {

    companion object {
        fun from(product: Product): ProductModel {
            return ProductModel(
                    id = product.base.id,
                    active = product.base.active,
                    createdDateTime = product.base.createdDateTime,
                    changedDateTime = product.base.changedDateTime,
                    version = product.base.version,
                    name = product.name,
                    basePrice = product.basePrice,
                    description = product.description,
                    itemCode = product.itemCode,
                    pictureStorageKey = product.pictureStorageKey
            )
        }
    }
}