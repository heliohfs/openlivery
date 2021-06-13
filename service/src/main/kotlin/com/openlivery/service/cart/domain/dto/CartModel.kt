package com.openlivery.service.cart.domain.dto

import com.openlivery.service.cart.domain.Cart
import java.math.BigDecimal

class CartModel(val products: Set<CartCatalogProductModel> = mutableSetOf()) {

    companion object {
        fun from(cart: Cart): CartModel {
            return CartModel(cart.products.map { CartCatalogProductModel.from(it) }.toSet())
        }
    }

    var totalValue: BigDecimal

    init {
        totalValue = products.fold(BigDecimal.ZERO) { total, product ->
            total.add(product.finalPrice.multiply(BigDecimal(product.amount)))
        }
    }
}