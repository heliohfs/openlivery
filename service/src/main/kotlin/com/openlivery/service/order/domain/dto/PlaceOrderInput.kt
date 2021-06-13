package com.openlivery.service.order.domain.dto

import com.openlivery.service.common.domain.Address
import com.openlivery.service.order.domain.Order
import com.openlivery.service.order.domain.OrderCustomerData
import java.math.BigDecimal

class PlaceOrderInput(
        val products: List<OrderProductInput>,
        val displayedTotalValue: BigDecimal,
        private val notes: String? = null
) {

    fun toOrder(address: Address, customerData: OrderCustomerData): Order {
        return Order(
                deliveryAddress = address,
                notes = notes,
                customerData = customerData,
        )
    }

}