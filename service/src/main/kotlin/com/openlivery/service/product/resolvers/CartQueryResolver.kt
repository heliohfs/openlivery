package com.openlivery.service.product.resolvers

import com.openlivery.service.common.auth.AuthProvider
import com.openlivery.service.product.domain.model.CartModel
import com.openlivery.service.product.service.CartService
import graphql.kickstart.tools.GraphQLQueryResolver
import org.springframework.stereotype.Component

@Component
class CartQueryResolver(
        private val auth: AuthProvider,
        private val cartService: CartService
) : GraphQLQueryResolver {

    fun cart() = cartService
            .getTransientCart(auth.id)
            .let(CartModel::from)

}