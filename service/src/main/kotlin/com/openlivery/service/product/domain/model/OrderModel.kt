package com.openlivery.service.product.domain.model

import com.openlivery.service.common.domain.model.AddressModel
import com.openlivery.service.product.domain.entity.Order
import com.openlivery.service.product.domain.enums.OrderNonCompletionReason
import com.openlivery.service.product.domain.enums.OrderStatus
import java.math.BigDecimal
import java.time.LocalDateTime

class OrderModel private constructor(
        val id: Long,
        val createdDateTime: LocalDateTime,
        val deliveryAddress: AddressModel,
        val orderCode: String,
        val deliveryFee: BigDecimal,
        val customerRating: Int?,
        val customerRatingReason: String?,
        val status: OrderStatus,
        val nonCompletionReason: OrderNonCompletionReason?,
        val couponApplied: String?,
        val total: BigDecimal,
        val notes: String?,
) {

    companion object {
        fun from(order: Order): OrderModel {
            return OrderModel(
                    id = order.base.id,
                    createdDateTime = order.base.createdDateTime,
                    notes = order.notes,
                    deliveryAddress = AddressModel.from(order.deliveryAddress),
                    orderCode = order.orderCode!!,
                    deliveryFee = order.deliveryFee,
                    customerRating = order.customerRating,
                    customerRatingReason = order.customerRatingReason,
                    status = order.status,
                    nonCompletionReason = order.nonCompletionReason,
                    couponApplied = order.couponApplied,
                    total = order.total,
            )
        }
    }

}