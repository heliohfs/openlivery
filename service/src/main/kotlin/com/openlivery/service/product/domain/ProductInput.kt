package com.openlivery.service.product.domain

import java.math.BigDecimal

class ProductInput(
        private val name: String,
        private val price: BigDecimal,
) {

    val id: Long? = null
    val categoriesIds: List<Long>? = null
    val brandId: Long? = null
    private val description: String? = null
    private val itemCode: String? = null

    fun toProduct(brand: Brand?, categories: List<Category>?): Product {
        return Product(
                name = name,
                price = price
        ).also {
                    it.description = this.description
                    it.itemCode = this.itemCode
                    it.brand = brand
                    it.categories = categories?.toMutableList() ?: it.categories
                }
    }

    fun updateProduct(product: Product, brand: Brand?, categories: List<Category>?): Product {
        return product.also {
            it.name = this.name
            it.price = this.price
            it.description = this.description ?: it.description
            it.itemCode = this.itemCode ?: it.itemCode
            it.brand = brand
            it.categories = categories?.toMutableList() ?: it.categories
        }
    }

}