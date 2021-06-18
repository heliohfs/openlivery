package com.openlivery.service.order.resolver

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.openlivery.service.common.auth.IAuthenticationFacade
import com.openlivery.service.order.domain.dto.OrderCartModel
import com.openlivery.service.order.service.CartService
import org.springframework.stereotype.Component

@Component("OrderCartQueryResolver")
class CartQueryResolver(
        private val auth: IAuthenticationFacade,
        private val cartService: CartService,
) : GraphQLQueryResolver {

    fun cart(): OrderCartModel {
        return cartService.getCart(auth.id)
    }

}