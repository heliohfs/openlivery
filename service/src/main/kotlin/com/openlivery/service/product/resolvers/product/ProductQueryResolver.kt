package com.openlivery.service.product.resolvers.product

import com.openlivery.service.common.domain.entity.Authority.Companion.READ_PRODUCTS
import com.openlivery.service.product.domain.model.BrandModel
import com.openlivery.service.product.domain.model.CatalogProductModel
import com.openlivery.service.product.domain.model.CategoryModel
import com.openlivery.service.product.domain.model.ProductModel
import com.openlivery.service.product.service.ProductService
import graphql.kickstart.tools.GraphQLQueryResolver
import graphql.relay.Connection
import graphql.relay.SimpleListConnection
import graphql.schema.DataFetchingEnvironment
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component

@Component
class ProductQueryResolver(
        private val productService: ProductService,
) : GraphQLQueryResolver {

    @PreAuthorize("hasAuthority('$READ_PRODUCTS')")
    fun products(first: Int, after: String, env: DataFetchingEnvironment): Connection<ProductModel> {
        return productService.findAllProducts()
                .map { ProductModel.from(it) }
                .let { SimpleListConnection(it).get(env) }
    }

    @PreAuthorize("hasAuthority('$READ_PRODUCTS')")
    fun productById(id: Long, env: DataFetchingEnvironment): ProductModel {
        return productService.findProductById(id)
                .orElseThrow { error("") }
                .let { ProductModel.from(it) }
    }

    @PreAuthorize("permitAll()")
    fun brands(first: Int, after: String, env: DataFetchingEnvironment): Connection<BrandModel> {
        return productService.findAllBrands()
                .map { BrandModel.from(it) }
                .let { SimpleListConnection(it).get(env) }
    }

    @PreAuthorize("permitAll()")
    fun categories(first: Int, after: String, env: DataFetchingEnvironment): Connection<CategoryModel> {
        return productService.findAllCategories()
                .map { CategoryModel.from(it) }
                .let { SimpleListConnection(it).get(env) }
    }

    @PreAuthorize("permitAll()")
    fun catalog(first: Int, after: String, env: DataFetchingEnvironment): Connection<CatalogProductModel> {
        return productService.findAllCatalogProducts()
                .map { CatalogProductModel.from(it) }
                .let { SimpleListConnection(it).get(env) }
    }

    @PreAuthorize("permitAll()")
    fun categoryById(id: Long, env: DataFetchingEnvironment): CategoryModel {
        return productService.findCategoryById(id)
                .orElseThrow { error("") }
                .let { CategoryModel.from(it) }
    }

    @PreAuthorize("permitAll()")
    fun brandById(id: Long, env: DataFetchingEnvironment): BrandModel {
        return productService.findBrandById(id)
                .orElseThrow { error("") }
                .let { BrandModel.from(it) }
    }

    @PreAuthorize("permitAll()")
    fun catalogProductById(id: Long, env: DataFetchingEnvironment): CatalogProductModel {
        return productService.findCatalogProductById(id)
                .orElseThrow { error("") }
                .let { CatalogProductModel.from(it) }
    }

}