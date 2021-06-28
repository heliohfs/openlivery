package com.openlivery.service.product.resolvers

import com.openlivery.service.product.domain.model.CartDeliveryAddressModel
import com.openlivery.service.product.domain.model.CartModel
import com.openlivery.service.product.service.ProductQueryService
import graphql.kickstart.tools.GraphQLResolver
import org.springframework.stereotype.Component

@Component
class CartResolver(
        private val productQueryService: ProductQueryService
) : GraphQLResolver<CartModel> {

    fun deliveryAddress(cartModel: CartModel) = cartModel.deliveryAddress?.run {
        CartDeliveryAddressModel.from(cartModel.deliveryAddress)
    }

}