package com.openlivery.service.order.repository

import com.openlivery.service.order.domain.Cart
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository("OrderCartRepository")
interface CartRepository : CrudRepository<Cart, Long> {
}