package com.openlivery.service.product.resolvers

import com.openlivery.service.product.domain.model.BrandModel
import com.openlivery.service.product.domain.model.CategoryModel
import com.openlivery.service.product.domain.model.ProductModel
import com.openlivery.service.product.service.ProductService
import graphql.kickstart.tools.GraphQLResolver
import org.springframework.stereotype.Component

@Component
class ProductResolver(
        private val productService: ProductService
) : GraphQLResolver<ProductModel> {

    fun brand(productModel: ProductModel): BrandModel? {
        return productService.findBrandByProductId(productModel.id)
                .map { BrandModel.from(it) }
                .orElse(null)
    }

    fun categories(productModel: ProductModel): List<CategoryModel> {
        return productService.findCategoriesByProductId(productModel.id)
                .map { CategoryModel.from(it) }
    }

}