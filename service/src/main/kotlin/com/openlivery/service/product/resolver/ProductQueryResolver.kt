package com.openlivery.service.product.resolver

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.openlivery.service.product.domain.Product
import com.openlivery.service.product.service.ProductService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component

@Component
class ProductQueryResolver(
        val service: ProductService
) : GraphQLQueryResolver {

    fun products(): List<Product> {
        return service.findAll().toList()
    }

}