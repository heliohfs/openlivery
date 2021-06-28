package com.openlivery.service.product.resolvers

import com.openlivery.service.common.domain.entity.Authority
import com.openlivery.service.product.domain.model.CategoryModel
import com.openlivery.service.product.domain.model.ProductModel
import com.openlivery.service.product.service.ProductQueryService
import graphql.kickstart.tools.GraphQLResolver
import graphql.relay.Connection
import graphql.relay.SimpleListConnection
import graphql.schema.DataFetchingEnvironment
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component

@Component
class CategoryResolver(
        private val productQueryService: ProductQueryService
) : GraphQLResolver<CategoryModel> {

    @PreAuthorize("hasAuthority('${Authority.READ_PRODUCTS}')")
    fun products(categoryModel: CategoryModel, first: Int, after: String, env: DataFetchingEnvironment): Connection<ProductModel> {
        return productQueryService.findProductsByCategoryId(categoryModel.id)
                .map { ProductModel.from(it) }
                .let { SimpleListConnection(it).get(env) }
    }

}