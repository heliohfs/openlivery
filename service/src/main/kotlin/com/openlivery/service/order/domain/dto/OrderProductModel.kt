package com.openlivery.service.order.domain.dto

class OrderProductModel(
        val name: String,
        val amount: Int,
        val pictureStorageKey: String? = null
)