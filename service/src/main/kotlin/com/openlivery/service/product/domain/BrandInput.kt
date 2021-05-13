package com.openlivery.service.product.domain

class BrandInput(
        private val name: String,
) {

    val productsIds: List<Long>? = null

    fun toBrand(products: List<Product>?): Brand {
        return Brand(name)
                .also {
                    it.products = products?.toMutableList()
                }
    }

    fun updateBrand(brand: Brand, products: List<Product>?): Brand {
        return brand.also {
            it.name = this.name
            it.products = products?.toMutableList() ?: it.products
        }
    }
}