package com.openlivery.service.product.resolvers.cart

import com.openlivery.service.product.domain.model.CartDeliveryAddressModel
import com.openlivery.service.product.domain.model.CartModel
import com.openlivery.service.product.domain.model.CartProductModel
import com.openlivery.service.product.service.ProductService
import graphql.kickstart.tools.GraphQLResolver
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component

@Component
class CartResolver(
    private val productService: ProductService
) : GraphQLResolver<CartModel> {

    @PreAuthorize("permitAll()")
    fun deliveryAddress(cartModel: CartModel) = cartModel.deliveryAddress?.run {
        CartDeliveryAddressModel.from(cartModel.deliveryAddress)
    }

}