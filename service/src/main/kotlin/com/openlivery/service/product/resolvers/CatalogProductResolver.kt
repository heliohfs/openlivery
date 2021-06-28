package com.openlivery.service.product.resolvers

import com.openlivery.service.product.domain.model.BrandModel
import com.openlivery.service.product.domain.model.CatalogProductModel
import com.openlivery.service.product.domain.model.CategoryModel
import com.openlivery.service.product.service.ProductQueryService
import graphql.kickstart.tools.GraphQLResolver
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component

@Component
class CatalogProductResolver(
        private val productQueryService: ProductQueryService
) : GraphQLResolver<CatalogProductModel> {

    @PreAuthorize("permitAll()")
    fun brand(catalogProductModel: CatalogProductModel): BrandModel? {
        return productQueryService.findBrandByProductId(catalogProductModel.id)
                .map { BrandModel.from(it) }
                .orElse(null)
    }

    @PreAuthorize("permitAll()")
    fun categories(catalogProductModel: CatalogProductModel): List<CategoryModel> {
        return productQueryService.findCategoriesByProductId(catalogProductModel.id)
                .map { CategoryModel.from(it) }
    }

}