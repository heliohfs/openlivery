package com.openlivery.service.cart.resolver

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.openlivery.service.cart.domain.Cart
import com.openlivery.service.cart.domain.dto.CartModel
import com.openlivery.service.cart.repository.CartCatalogProductRepository
import com.openlivery.service.cart.service.CartService
import com.openlivery.service.common.auth.IAuthenticationFacade
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component
import java.util.*

@Component
class CartMutationResolver(
        private val auth: IAuthenticationFacade,
        private val cartService: CartService,
        private val cartCatalogProductRepository: CartCatalogProductRepository
) : GraphQLMutationResolver {

    @PreAuthorize("isAuthenticated()")
    fun addProductToCart(productId: Long, amount: Int): CartModel {
        if (amount < 0)
            throw error("Product amount must be greater than 0.")

        val product = cartCatalogProductRepository.findById(productId)
                .orElseThrow { error("Product not found.") }
                .apply { this.amount = amount }

        val userId = Optional.ofNullable(auth.user.id)
                .orElseThrow { error("User does not exist.") }

        return cartService.findById(userId)
                .orElse(Cart(userId))
                .apply { this.products.add(product) }
                .run { cartService.save(this) }
                .let { CartModel.from(it) }
    }

    fun removeProductFromCart(productId: Long): CartModel {
        val userId = Optional.ofNullable(auth.user.id)
                .orElseThrow { error("User does not exist.") }
        val cart = cartService.findById(userId)
                .orElse(Cart(userId))
                .apply { this.products.removeAll { it.id == productId } }
        return cartService.save(cart)
                .let { CartModel.from(it) }
    }

    fun increaseProductAmount(productId: Long): CartModel {
        val userId = Optional.ofNullable(auth.user.id)
                .orElseThrow { error("User does not exist.") }
        return cartService.findById(userId)
                .orElseThrow { error("Product not in cart") }
                .let { cart ->
                    cart.apply {
                        val product = Optional.ofNullable(this.products.find { product -> product.id == productId })
                                .orElseThrow { error("Product not in cart") }
                        product.amount++
                    }
                    CartModel.from(cartService.save(cart))
                }
    }

    fun decreaseProductAmount(productId: Long): CartModel {
        val userId = Optional.ofNullable(auth.user.id)
                .orElseThrow { error("User does not exist.") }
        return cartService.findById(userId)
                .orElseThrow { error("Product not in cart") }
                .let { cart ->
                    cart.apply {
                        val product = Optional.ofNullable(this.products.find { product -> product.id == productId })
                                .orElseThrow { error("Product not in cart") }
                        product.amount--
                    }
                    CartModel.from(cartService.save(cart))
                }
    }
}