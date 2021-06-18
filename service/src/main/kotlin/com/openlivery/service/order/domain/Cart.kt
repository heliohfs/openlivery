package com.openlivery.service.order.domain

import org.springframework.data.redis.core.RedisHash
import java.io.Serializable
import java.math.BigDecimal

@RedisHash("OrderCart")
data class Cart(
        val id: String,
) : Serializable {

    class Product(
            val id: Long,
            var amount: Int,
    ) : Serializable

    var appliedCoupon: String? = null

    var products: MutableSet<Product> = mutableSetOf()

}