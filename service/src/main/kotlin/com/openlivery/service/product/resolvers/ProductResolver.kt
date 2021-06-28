package com.openlivery.service.product.resolvers

import com.openlivery.service.product.domain.model.BrandModel
import com.openlivery.service.product.domain.model.CategoryModel
import com.openlivery.service.product.domain.model.ProductModel
import com.openlivery.service.product.service.ProductQueryService
import graphql.kickstart.tools.GraphQLResolver
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component

@Component
class ProductResolver(
        private val productQueryService: ProductQueryService
) : GraphQLResolver<ProductModel> {

    @PreAuthorize("permitAll()")
    fun brand(productModel: ProductModel): BrandModel? {
        return productQueryService.findBrandByProductId(productModel.id)
                .map { BrandModel.from(it) }
                .orElse(null)
    }

    @PreAuthorize("permitAll()")
    fun categories(productModel: ProductModel): List<CategoryModel> {
        return productQueryService.findCategoriesByProductId(productModel.id)
                .map { CategoryModel.from(it) }
    }

}