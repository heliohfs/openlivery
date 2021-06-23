package com.openlivery.service.product.resolvers

import com.openlivery.service.product.domain.model.BrandModel
import com.openlivery.service.product.domain.model.ProductModel
import com.openlivery.service.product.service.ProductService
import graphql.kickstart.tools.GraphQLResolver
import graphql.relay.Connection
import graphql.relay.SimpleListConnection
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component

@Component
class BrandResolver(
        private val productService: ProductService
) : GraphQLResolver<BrandModel> {

    fun products(brandModel: BrandModel, first: Int, after: String, env: DataFetchingEnvironment): Connection<ProductModel> {
        return productService.findProductsByCategoryId(brandModel.id)
                .map { ProductModel.from(it) }
                .let { SimpleListConnection(it).get(env) }
    }

}