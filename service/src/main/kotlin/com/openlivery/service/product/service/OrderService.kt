package com.openlivery.service.product.service

import com.openlivery.service.product.domain.entity.Order
import com.openlivery.service.product.repository.OrderRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class OrderService(
        private val orderRepository: OrderRepository,
        private val cartService: CartService
) {

    fun createOrder(): Order {
        TODO("")
    }

    fun findOrderById(id: Long): Optional<Order> {
        TODO("")
    }

    fun findAllOrdersByIdentityNumber(): List<Order> {
        TODO("")
    }



}