package com.openlivery.service.order.service

import com.openlivery.service.common.domain.Address
import com.openlivery.service.order.domain.CustomerData
import com.openlivery.service.order.domain.Order
import com.openlivery.service.order.domain.OrderProduct
import com.openlivery.service.order.repository.OrderRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Service
class OrderService(
        private val repository: OrderRepository,
) {

    @Transactional
    fun createOrder(
            totalValue: BigDecimal,
            deliveryFee: BigDecimal,
            products: List<OrderProduct>,
            customerData: CustomerData,
            address: Address,
            coupon: String? = null,
            notes: String?
    ) = Order(address, notes, customerData)
            .run { repository.save(this) }
            .apply { orderValue = totalValue.plus(deliveryFee) }
            .apply { couponApplied = coupon }
            .apply { orderProducts = products.map { it.orderId = id; it }.toMutableSet() }
            .run { repository.save(this) }

}