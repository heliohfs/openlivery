package com.openlivery.service.product.resolvers.cart

import com.openlivery.service.common.auth.AuthProvider
import com.openlivery.service.common.domain.entity.Customer
import com.openlivery.service.product.domain.model.CartModel
import com.openlivery.service.product.service.CartService
import graphql.kickstart.tools.GraphQLQueryResolver
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component

@Component
class CartQueryResolver(
        private val auth: AuthProvider,
        private val cartService: CartService
) : GraphQLQueryResolver {

    @PreAuthorize("permitAll()")
    fun cart() = cartService
            .getTransientCart(auth.id, auth.user as? Customer)
            .let(CartModel::from)

}