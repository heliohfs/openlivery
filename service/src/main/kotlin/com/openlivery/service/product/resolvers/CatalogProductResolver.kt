package com.openlivery.service.product.resolvers

import com.openlivery.service.product.domain.model.BrandModel
import com.openlivery.service.product.domain.model.CatalogProductModel
import com.openlivery.service.product.domain.model.CategoryModel
import com.openlivery.service.product.service.ProductService
import graphql.kickstart.tools.GraphQLResolver
import org.springframework.stereotype.Component

@Component
class CatalogProductResolver(
        private val productService: ProductService
) : GraphQLResolver<CatalogProductModel> {

    fun brand(catalogProductModel: CatalogProductModel): BrandModel? {
        return productService.findBrandByProductId(catalogProductModel.id)
                .map { BrandModel.from(it) }
                .orElse(null)
    }

    fun categories(catalogProductModel: CatalogProductModel): List<CategoryModel> {
        return productService.findCategoriesByProductId(catalogProductModel.id)
                .map { CategoryModel.from(it) }
    }

}