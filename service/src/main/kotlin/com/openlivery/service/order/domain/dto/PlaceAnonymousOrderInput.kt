package com.openlivery.service.order.domain.dto

import com.openlivery.service.common.domain.Address
import com.openlivery.service.order.domain.Order
import com.openlivery.service.order.domain.OrderCustomerData
import com.openlivery.service.order.domain.OrderProduct
import org.apache.commons.lang3.tuple.MutablePair
import java.math.BigDecimal

class PlaceAnonymousOrderInput(
        val completeName: String,
        val phoneNumber: String,
        val streetName: String,
        val streetNumber: Int,
        val cityName: String,
        val governingDistrict: String,
        val country: String,
        val additionalInfo: String,
        val products: List<OrderProductInput>,
        val displayedTotalValue: BigDecimal,
        private val notes: String? = null
) {

    fun toOrder(
            address: Address,
            customerData: OrderCustomerData,
    ): Order {
        return Order(
                deliveryAddress = address,
                notes = notes,
                customerData = customerData,
        )
    }

}