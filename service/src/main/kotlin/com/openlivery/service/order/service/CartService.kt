package com.openlivery.service.order.service

import com.openlivery.service.order.domain.Cart
import com.openlivery.service.order.repository.CartRepository
import org.springframework.stereotype.Service
import java.util.*

@Service("OrderCartService")
class CartService(
        val repository: CartRepository
) {

    fun save(cart: Cart): Cart {
        return repository.save(cart)
    }

    fun findByUser(id: Long): Optional<Cart> {
        return repository.findById(id)
    }

}