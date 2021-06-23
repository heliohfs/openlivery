package com.openlivery.service.product.resolvers

import com.openlivery.service.product.domain.model.ProductModel
import com.openlivery.service.product.service.ProductService
import graphql.kickstart.tools.GraphQLQueryResolver
import graphql.relay.Connection
import graphql.relay.SimpleListConnection
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component

@Component
class Query(
        private val productService: ProductService
): GraphQLQueryResolver {

    fun products(first: Int, after: String, env: DataFetchingEnvironment): Connection<ProductModel> {
        return productService.findAll()
                .map { ProductModel.from(it) }
                .let { SimpleListConnection(it).get(env) }
    }

}