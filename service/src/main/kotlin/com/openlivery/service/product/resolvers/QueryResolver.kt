package com.openlivery.service.product.resolvers

import com.openlivery.service.product.domain.model.BrandModel
import com.openlivery.service.product.domain.model.CatalogProductModel
import com.openlivery.service.product.domain.model.CategoryModel
import com.openlivery.service.product.domain.model.ProductModel
import com.openlivery.service.product.service.ProductService
import graphql.kickstart.tools.GraphQLQueryResolver
import graphql.relay.Connection
import graphql.relay.SimpleListConnection
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component

@Component
class QueryResolver(
        private val productService: ProductService
) : GraphQLQueryResolver {

    fun products(first: Int, after: String, env: DataFetchingEnvironment): Connection<ProductModel> {
        return productService.findAllProducts()
                .map { ProductModel.from(it) }
                .let { SimpleListConnection(it).get(env) }
    }

    fun brands(first: Int, after: String, env: DataFetchingEnvironment): Connection<BrandModel> {
        return productService.findAllBrands()
                .map { BrandModel.from(it) }
                .let { SimpleListConnection(it).get(env) }
    }

    fun categories(first: Int, after: String, env: DataFetchingEnvironment): Connection<CategoryModel> {
        return productService.findAllCategories()
                .map { CategoryModel.from(it) }
                .let { SimpleListConnection(it).get(env) }
    }

    fun catalog(first: Int, after: String, env: DataFetchingEnvironment): Connection<CatalogProductModel> {
        return productService.findAllCatalogProducts()
                .map { CatalogProductModel.from(it) }
                .let { SimpleListConnection(it).get(env) }
    }

}