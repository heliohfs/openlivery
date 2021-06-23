package com.openlivery.service.product.domain.model

import com.openlivery.service.product.domain.entity.Category

class CategoryModel private constructor(
        val id: Long? = null,
        val categoryName: String,
        val products: List<ProductModel> = listOf()
) {

    companion object {
        fun from(category: Category): CategoryModel {
            return CategoryModel(
                    categoryName = category.categoryName
            )
        }
    }
}