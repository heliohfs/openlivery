package com.openlivery.service.order.resolver

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.openlivery.service.common.auth.IAuthenticationFacade
import com.openlivery.service.order.service.CartService
import org.springframework.stereotype.Component

@Component("OrderCartMutationResolver")
class CartMutationResolver(
        private val auth: IAuthenticationFacade,
        private val cartService: CartService,
) : GraphQLMutationResolver {

    fun addProductToCart(productId: Long, amount: Int) {
        cartService.addProduct(auth.id, productId, amount)
    }

    fun removeProductFromCart(productId: Long) {
        cartService.removeProduct(auth.id, productId)
    }

    fun increaseProductAmount(productId: Long) {
        cartService.increaseProductAmount(auth.id, productId)
    }

    fun decreaseProductAmount(productId: Long) {
        cartService.decreaseProductAmount(auth.id, productId)
    }

    fun applyCoupon(couponCode: String) {
        cartService.applyCoupon(auth.isAnonymous, auth.id, couponCode)
    }

    fun removeCoupon() {
        cartService.removeCoupon(auth.id)
    }

    fun clearCart() {
        cartService.clearCart(auth.id)
    }

}