package com.openlivery.service.cart.service

import com.openlivery.service.cart.domain.Cart
import com.openlivery.service.cart.repository.CartRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class CartService(
        val repository: CartRepository
) {

    fun save(cart: Cart): Cart {
        return repository.save(cart)
    }

    fun findById(id: Long): Optional<Cart> {
        return repository.findById(id)
    }

}