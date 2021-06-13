package com.openlivery.service.cart.repository

import com.openlivery.service.cart.domain.Cart
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CartRepository: CrudRepository<Cart, Long> {
}