package com.openlivery.service.product.resolvers

import com.openlivery.service.common.auth.AuthProvider
import com.openlivery.service.common.domain.model.AddressInput
import com.openlivery.service.product.domain.model.CartModel
import com.openlivery.service.product.service.CartService
import graphql.kickstart.tools.GraphQLMutationResolver
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component

@Component
class CartMutationResolver(
        private val auth: AuthProvider,
        private val cartService: CartService
) : GraphQLMutationResolver {

    @PreAuthorize("permitAll()")
    fun addProductToCart(productId: Long, amount: Int) = cartService
            .addProductToCart(auth.id, productId, amount)
            .let(CartModel::from)

    @PreAuthorize("permitAll()")
    fun removeProductFromCart(productId: Long) = cartService
            .removeProductFromCart(auth.id, productId)
            .let(CartModel::from)

    @PreAuthorize("permitAll()")
    fun increaseCartProductAmount(productId: Long, amount: Int) = cartService
            .increaseCartProductAmount(auth.id, productId, amount)
            .let(CartModel::from)

    @PreAuthorize("permitAll()")
    fun decreaseCartProductAmount(productId: Long, amount: Int) = cartService
            .decreaseCartProductAmount(auth.id, productId, amount)
            .let(CartModel::from)

    @PreAuthorize("permitAll()")
    fun setCartExistingDeliveryAddress(addressId: Long) = cartService
            .setCartExistingDeliveryAddress(auth.id, addressId)
            .let(CartModel::from)

    @PreAuthorize("permitAll()")
    fun setCartNewDeliveryAddress(address: AddressInput) = cartService
            .setCartNewDeliveryAddress(auth.id, address)
            .let(CartModel::from)

    @PreAuthorize("permitAll()")
    fun applyCoupon(couponCode: String) = cartService
            .applyCouponToCart(auth.id, couponCode, auth.isAnonymous)
            .let(CartModel::from)

    @PreAuthorize("permitAll()")
    fun removeCoupon() = cartService
            .removeCouponFromCart(auth.id)
            .let(CartModel::from)

    @PreAuthorize("permitAll()")
    fun clearCart() = cartService
            .clearCart(auth.id)
            .let(CartModel::from)
}