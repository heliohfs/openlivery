package com.openlivery.service.product.resolvers

import com.openlivery.service.common.domain.entity.Authority
import com.openlivery.service.product.domain.model.BrandModel
import com.openlivery.service.product.domain.model.ProductModel
import com.openlivery.service.product.service.ProductQueryService
import graphql.kickstart.tools.GraphQLResolver
import graphql.relay.Connection
import graphql.relay.SimpleListConnection
import graphql.schema.DataFetchingEnvironment
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component

@Component
class BrandResolver(
        private val productQueryService: ProductQueryService
) : GraphQLResolver<BrandModel> {

    @PreAuthorize("hasAuthority('${Authority.READ_PRODUCTS}')")
    fun products(brandModel: BrandModel, first: Int, after: String, env: DataFetchingEnvironment): Connection<ProductModel> {
        return productQueryService.findProductsByCategoryId(brandModel.id)
                .map { ProductModel.from(it) }
                .let { SimpleListConnection(it).get(env) }
    }

}