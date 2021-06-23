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
        val price: BigDecimal,
        val categoriesIds: List<Long>,
        val description: String? = null,
        val itemCode: String? = null,
        val pictureStorageKey: String? = null,
        val brandId: Long? = null
) {

    companion object {
        fun from(product: Product): ProductModel {
            return ProductModel(
                    id = product.id!!,
                    active = product.active!!,
                    createdDateTime = product.createdDateTime!!,
                    changedDateTime = product.changedDateTime!!,
                    version = product.version!!,
                    name = product.name,
                    price = product.price,
                    categoriesIds = product.categories.map { it.id!! },
                    description = product.description,
                    itemCode = product.itemCode,
                    pictureStorageKey = product.pictureStorageKey,
                    brandId = product.brand?.id
            )
        }
    }
}