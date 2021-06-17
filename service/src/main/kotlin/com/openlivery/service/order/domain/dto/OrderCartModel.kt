package com.openlivery.service.order.domain.dto

import java.math.BigDecimal

class OrderCartModel(
        val totalValue: BigDecimal,
        val deliveryFee: BigDecimal,
        val products: Set<OrderCartProductModel>,
        val couponApplied: String? = null
)
