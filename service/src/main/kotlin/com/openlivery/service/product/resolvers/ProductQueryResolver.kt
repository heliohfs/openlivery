package com.openlivery.service.product.resolvers

import com.openlivery.service.common.auth.AuthProvider
import com.openlivery.service.common.domain.entity.Authority.Companion.READ_PRODUCTS
import com.openlivery.service.product.domain.model.*
import com.openlivery.service.product.service.CartService

import com.openlivery.service.product.service.ProductQueryService
import graphql.kickstart.tools.GraphQLQueryResolver
import graphql.relay.Connection
import graphql.relay.SimpleListConnection
import graphql.schema.DataFetchingEnvironment
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component

@Component
class ProductQueryResolver(
        private val auth: AuthProvider,
        private val productQueryService: ProductQueryService,
        private val cartService: CartService
) : GraphQLQueryResolver {

    @PreAuthorize("hasAuthority('$READ_PRODUCTS')")
    fun products(first: Int, after: String, env: DataFetchingEnvironment): Connection<ProductModel> {
        return productQueryService.findAllProducts()
                .map { ProductModel.from(it) }
                .let { SimpleListConnection(it).get(env) }
    }

    @PreAuthorize("hasAuthority('$READ_PRODUCTS')")
    fun productById(id: Long, env: DataFetchingEnvironment): ProductModel {
        return productQueryService.findProductById(id)
                .orElseThrow { error("") }
                .let { ProductModel.from(it) }
    }

    @PreAuthorize("permitAll()")
    fun brands(first: Int, after: String, env: DataFetchingEnvironment): Connection<BrandModel> {
        return productQueryService.findAllBrands()
                .map { BrandModel.from(it) }
                .let { SimpleListConnection(it).get(env) }
    }

    @PreAuthorize("permitAll()")
    fun categories(first: Int, after: String, env: DataFetchingEnvironment): Connection<CategoryModel> {
        return productQueryService.findAllCategories()
                .map { CategoryModel.from(it) }
                .let { SimpleListConnection(it).get(env) }
    }

    @PreAuthorize("permitAll()")
    fun catalog(first: Int, after: String, env: DataFetchingEnvironment): Connection<CatalogProductModel> {
        return productQueryService.findAllCatalogProducts()
                .map { CatalogProductModel.from(it) }
                .let { SimpleListConnection(it).get(env) }
    }

    @PreAuthorize("permitAll()")
    fun categoryById(id: Long, env: DataFetchingEnvironment): CategoryModel {
        return productQueryService.findCategoryById(id)
                .orElseThrow { error("") }
                .let { CategoryModel.from(it) }
    }

    @PreAuthorize("permitAll()")
    fun brandById(id: Long, env: DataFetchingEnvironment): BrandModel {
        return productQueryService.findBrandById(id)
                .orElseThrow { error("") }
                .let { BrandModel.from(it) }
    }

    @PreAuthorize("permitAll()")
    fun catalogProductById(id: Long, env: DataFetchingEnvironment): CatalogProductModel {
        return productQueryService.findCatalogProductById(id)
                .orElseThrow { error("") }
                .let { CatalogProductModel.from(it) }
    }

    @PreAuthorize("permitAll()")
    fun cart(): CartModel {
        val cartId = auth.id
        return cartService.getCart(cartId)
                .let { CartModel.from(it) }
    }
}