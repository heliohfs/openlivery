package com.openlivery.service.product.repository

import com.openlivery.service.product.domain.entity.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface OrderRepository : JpaRepository<Order, Long> {

    fun findByOrderCode(orderCode: String): Optional<Order>

}