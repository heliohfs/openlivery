package com.openlivery.service.product.domain.model

import com.openlivery.service.product.domain.entity.Brand

class BrandModel private constructor(
        val id: Long? = null,
        val name: String,
        val products: List<ProductModel> = listOf()
) {

    companion object {
        fun from(brand: Brand): BrandModel {
            return BrandModel(
                    name = brand.name
            )
        }
    }
}