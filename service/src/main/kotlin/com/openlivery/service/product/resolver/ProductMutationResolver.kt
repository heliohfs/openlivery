package com.openlivery.service.product.resolver

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.openlivery.service.product.domain.Product
import com.openlivery.service.product.domain.ProductInput
import com.openlivery.service.product.service.BrandService
import com.openlivery.service.product.service.ProductService
import org.springframework.stereotype.Component

@Component
class ProductMutationResolver(
        val service: ProductService,
        val brandService: BrandService
) : GraphQLMutationResolver {

    fun upsertProduct(productInput: ProductInput): Product {
        val brand = productInput.brandId?.let {
            brandService.findById(it)
                    .orElseThrow { error("Brand not found") }
        }

        val product = productInput.id?.let {
            val product = service.findById(it)
                    .orElseThrow { error("Product not found") }
            productInput.updateProduct(product, brand)
        } ?: productInput.toProduct(brand)

        return service.save(product)
    }

}