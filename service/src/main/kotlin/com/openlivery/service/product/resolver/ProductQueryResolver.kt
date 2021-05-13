package com.openlivery.service.product.resolver

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.openlivery.service.product.domain.Product
import com.openlivery.service.product.service.ProductService
import org.springframework.stereotype.Component

@Component
class ProductQueryResolver(
        val service: ProductService
) : GraphQLQueryResolver {

    fun products(): List<Product> {
        return service.findAll()
    }

    fun productById(id: Long): Product {
        return service.findById(id)
                .orElseThrow { error("Product not found") }
    }

}