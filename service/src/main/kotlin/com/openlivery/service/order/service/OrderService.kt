package com.openlivery.service.order.service

import com.openlivery.service.order.domain.Order
import com.openlivery.service.order.repository.OrderRepository
import org.springframework.stereotype.Service

@Service
class OrderService(
        val repository: OrderRepository
) {

    fun save(order: Order): Order {
        return repository.save(order)
    }

}