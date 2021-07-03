package com.openlivery.service.product.domain.model

import com.openlivery.service.product.domain.entity.Order
import com.openlivery.service.product.domain.enums.OrderStatus
import java.math.BigDecimal
import java.time.LocalDateTime

class AnonymousOrderInfoModel private constructor(
        val id: Long,
        val deliveryAddress: String,
        val createdDateTime: LocalDateTime,
        val customerName: String,
        val orderValue: BigDecimal,
        val status: OrderStatus,
        val notes: String?
) {

    companion object {
        fun from(order: Order): AnonymousOrderInfoModel {
            return AnonymousOrderInfoModel(
                    id = order.base.id,
                    deliveryAddress = order.deliveryAddress.toString(),
                    createdDateTime = order.base.createdDateTime,
                    customerName = order.customerData.completeName,
                    orderValue = order.total,
                    status = order.status,
                    notes = order.notes,
            )
        }
    }

}