package com.openlivery.service.product.resolver

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.openlivery.service.product.domain.Product
import com.openlivery.service.product.domain.ProductInput
import com.openlivery.service.product.service.BrandService
import com.openlivery.service.product.service.CategoryService
import com.openlivery.service.product.service.ProductService
import org.springframework.stereotype.Component

@Component
class ProductMutationResolver(
        val service: ProductService,
        val brandService: BrandService,
        val categoryService: CategoryService
) : GraphQLMutationResolver {

    fun upsertProduct(productInput: ProductInput): Product {
        val brand = productInput.brandId?.let {
            brandService.findById(it)
                    .orElseThrow { error("Brand not found") }
        }

        val categories = productInput.categoriesIds?.let {
            categoryService.findAllByIdIn(productInput.categoriesIds)
        }

        val product = productInput.id?.let {
            val product = service.findById(it)
                    .orElseThrow { error("Product not found") }
            productInput.updateProduct(
                    product = product,
                    brand = brand,
                    categories = categories
            )
        } ?: productInput.toProduct(
                brand = brand,
                categories = categories
        )

        return service.save(product)
    }

    fun deleteProductById(id: Long): Boolean {
        service.deleteById(id)
        return true
    }

}