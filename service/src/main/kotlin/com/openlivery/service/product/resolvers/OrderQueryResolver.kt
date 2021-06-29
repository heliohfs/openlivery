package com.openlivery.service.product.resolvers

import com.openlivery.service.common.auth.AuthProvider
import com.openlivery.service.product.service.OrderService
import graphql.kickstart.tools.GraphQLQueryResolver
import org.springframework.stereotype.Component

@Component
class OrderQueryResolver(
        val auth: AuthProvider,
        val orderService: OrderService
) : GraphQLQueryResolver {

    fun orderById(id: Long) = orderService.findOrderById(id)

    fun placedOrders(first: Int, after: String) = orderService.findAllOrdersBy()

}