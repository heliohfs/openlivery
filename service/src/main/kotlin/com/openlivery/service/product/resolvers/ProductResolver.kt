package com.openlivery.service.product.resolvers

import com.openlivery.service.product.domain.entity.Brand
import com.openlivery.service.product.domain.entity.Category
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
        return productModel.brandId?.let { id ->
            productService.findBrandById(id)
                    .orElseThrow { error("Brand not found") }
                    .let { BrandModel.from(it) }
        }
    }

    fun categories(productModel: ProductModel): List<CategoryModel> {
        return productModel.categoriesIds.map { id ->
            productService.findCategoryById(id)
                    .orElseThrow { error("Category not found") }
                    .let { CategoryModel.from(it) }
        }
    }

}