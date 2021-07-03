package com.openlivery.service.product.resolvers.order

import com.openlivery.service.common.auth.AuthProvider
import com.openlivery.service.product.domain.model.AnonymousOrderInfoModel
import com.openlivery.service.product.service.OrderService
import graphql.kickstart.tools.GraphQLQueryResolver
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component

@Component
class OrderQueryResolver(
        val auth: AuthProvider,
        val orderService: OrderService
) : GraphQLQueryResolver {

    @PreAuthorize("permitAll()")
    fun orderByCode(code: String): AnonymousOrderInfoModel {
        return orderService.findOrderByCode(code)
                .orElseThrow { error("") }
                .let { AnonymousOrderInfoModel.from(it) }
    }

}