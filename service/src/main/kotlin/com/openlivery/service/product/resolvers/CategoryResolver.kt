package com.openlivery.service.product.resolvers

import com.openlivery.service.product.domain.model.CategoryModel
import com.openlivery.service.product.domain.model.ProductModel
import com.openlivery.service.product.service.ProductService
import graphql.kickstart.tools.GraphQLResolver
import graphql.relay.Connection
import graphql.relay.SimpleListConnection
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component

@Component
class CategoryResolver(
        private val productService: ProductService
) : GraphQLResolver<CategoryModel> {

    fun products(categoryModel: CategoryModel, first: Int, after: String, env: DataFetchingEnvironment): Connection<ProductModel> {
        return productService.findProductsByCategoryId(categoryModel.id)
                .map { ProductModel.from(it) }
                .let { SimpleListConnection(it).get(env) }
    }

}