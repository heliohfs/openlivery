package com.openlivery.service.product.domain

import java.math.BigDecimal

class ProductInput(
        val id: Long? = null,
        val name: String? = null,
        val price: BigDecimal? = null,
        val description: String? = null,
        val itemCode: String? = null,
        val brandId: Long? = null
) {

    fun toProduct(brand: Brand? = null): Product {
        return Product().also {
                    it.name = this.name
                    it.price = this.price
                    it.description = this.description
                    it.itemCode = this.itemCode
                    it.brand = brand
                }
    }

    fun updateProduct(product: Product, brand: Brand? = null): Product {
        return product.apply {
            this.name = name ?: this.name
            this.price = price ?: this.price
            this.description = description ?: this.description
            this.itemCode = itemCode ?: this.itemCode
            this.brand = brand
        }
    }

}