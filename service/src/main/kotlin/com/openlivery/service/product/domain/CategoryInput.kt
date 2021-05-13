package com.openlivery.service.product.domain

class CategoryInput(
        private val categoryName: String,
) {

    val productsIds: List<Long>? = null

    fun toCategory(products: List<Product>?): Category {
        return Category(categoryName)
                .also {
                    it.products = products?.toMutableList()
                }
    }

    fun updateCategory(category: Category, products: List<Product>?): Category {
        return category.also {
            it.categoryName = this.categoryName
            it.products = products?.toMutableList() ?: it.products
        }
    }

}