package com.openlivery.service.catalog.resolvers

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.openlivery.service.catalog.domain.ProductCatalogItem
import com.openlivery.service.catalog.service.CatalogService
import org.springframework.stereotype.Component

@Component
class CatalogQueryResolver(
        val service: CatalogService
) : GraphQLQueryResolver {

    fun catalog(): List<ProductCatalogItem> {
        return service.findAll()
    }

    fun catalogItemById(productId: Long): ProductCatalogItem {
        return service.findById(productId)
                .orElseThrow { error("Product not found") }
    }

    fun catalogByCategoryName(categoryName: String): List<ProductCatalogItem> {
        return service.findAllByCategoryName(categoryName)
    }

}