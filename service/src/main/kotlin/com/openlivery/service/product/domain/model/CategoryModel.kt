package com.openlivery.service.product.domain.model

import com.openlivery.service.product.domain.entity.Category

class CategoryModel private constructor(
        val id: Long,
        val categoryName: String,
) {

    companion object {
        fun from(category: Category): CategoryModel {
            return CategoryModel(
                    id = category.base.id,
                    categoryName = category.categoryName
            )
        }
    }
}