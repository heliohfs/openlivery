package com.openlivery.service.order.resolver

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.openlivery.service.common.auth.IAuthenticationFacade
import com.openlivery.service.order.domain.Cart
import com.openlivery.service.order.service.CartService
import com.openlivery.service.order.service.CatalogProductService
import com.openlivery.service.order.service.CouponService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component
import java.util.*

@Component("OrderCartMutationResolver")
class CartMutationResolver(
        private val auth: IAuthenticationFacade,
        private val cartService: CartService,
        private val catalogProductService: CatalogProductService,
        private val couponService: CouponService
) : GraphQLMutationResolver {

    @PreAuthorize("isAuthenticated()")
    fun addProductToCart(productId: Long, amount: Int): Boolean {
        if (amount < 0) throw error("Product amount must be greater than 0.")

        val userId = Optional.ofNullable(auth.user.id)
                .orElseThrow { error("User does not exist.") }

        if (catalogProductService.existsById(productId)) {
            cartService.findByUser(userId)
                    .orElseGet { Cart(userId) }
                    .apply { products.add(Cart.Product(productId, amount)) }
                    .run { cartService.save(this) }
        } else throw error("Product does not exists.")

        return true
    }

    fun removeProductFromCart(productId: Long): Boolean {
        val userId = Optional.ofNullable(auth.user.id)
                .orElseThrow { error("User does not exist.") }

        cartService.findByUser(userId)
                .orElse(Cart(userId))
                .apply { this.products.removeAll { it.id == productId } }

        return true
    }

    fun increaseProductAmount(productId: Long): Boolean {
        val userId = Optional.ofNullable(auth.user.id)
                .orElseThrow { error("User does not exist.") }

        val cart = cartService.findByUser(userId)
                .orElseThrow { error("Product not in cart") }

        val product = Optional.ofNullable(cart.products.find { product -> product.id == productId })
                .orElseThrow { error("Product not in cart") }

        product.amount += 1

        return true
    }

    fun decreaseProductAmount(productId: Long): Boolean {
        val userId = Optional.ofNullable(auth.user.id)
                .orElseThrow { error("User does not exist.") }

        val cart = cartService.findByUser(userId)
                .orElseThrow { error("Product not in cart") }

        val product = Optional.ofNullable(cart.products.find { product -> product.id == productId })
                .orElseThrow { error("Product not in cart") }

        if(product.amount > 0)
            product.amount -= 1

        return true
    }

    fun applyCoupon(couponCode: String): Boolean {
        val userId = Optional.ofNullable(auth.user.id)
                .orElseThrow { error("User does not exist.") }

        val cart = cartService.findByUser(userId)
                .orElse(Cart(userId))

        couponService.findByCode(couponCode)
                .orElseThrow { error("Coupon does not exist or is not active.") }

        cart.appliedCoupon = couponCode

        cartService.save(cart)

        return true
    }
}