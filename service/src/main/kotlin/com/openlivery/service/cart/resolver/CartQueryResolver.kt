package com.openlivery.service.cart.resolver

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.openlivery.service.cart.domain.Cart
import com.openlivery.service.cart.domain.dto.CartModel
import com.openlivery.service.cart.service.CartService
import com.openlivery.service.common.auth.IAuthenticationFacade
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component
import java.util.*

@Component
class CartQueryResolver(
        private val auth: IAuthenticationFacade,
        private val cartService: CartService
) : GraphQLQueryResolver {

    @PreAuthorize("isAuthenticated()")
    fun cart(): CartModel {
        val userId = Optional.ofNullable(auth.user.id)
                .orElseThrow { error("User does not exist.") }

        return cartService.findById(userId)
                .orElse(Cart(userId))
                .let { CartModel.from(it) }
    }

}