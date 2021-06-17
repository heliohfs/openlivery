package com.openlivery.service.order.domain.dto

import java.math.BigDecimal

class OrderCartProductModel(
        val id: Long,
        val finalPrice: BigDecimal,
        val basePrice: BigDecimal,
        val pictureStorageKey: String,
        val amount: Int,
        val discountApplied: Boolean
)