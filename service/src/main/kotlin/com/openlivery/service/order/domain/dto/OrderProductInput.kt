package com.openlivery.service.order.domain.dto

import java.math.BigDecimal

class OrderProductInput(
        val productId: Long,
        val displayedValue: BigDecimal,
        val amount: Int
)