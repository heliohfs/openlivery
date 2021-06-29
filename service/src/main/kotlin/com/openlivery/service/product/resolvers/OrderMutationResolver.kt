package com.openlivery.service.product.resolvers

import com.openlivery.service.common.auth.AuthProvider
import com.openlivery.service.product.domain.entity.Order
import graphql.kickstart.tools.GraphQLMutationResolver
import org.springframework.stereotype.Component

@Component
class OrderMutationResolver(
        val auth: AuthProvider
) : GraphQLMutationResolver {

    fun placeOrder(notes: String?): Order {
        TODO("")
    }

}