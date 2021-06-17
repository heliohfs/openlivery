package com.openlivery.service.order.domain

import org.springframework.data.redis.core.RedisHash
import java.io.Serializable

@RedisHash("OrderCart")
data class Cart(
        val id: Long,
        var appliedCoupon: String? = null,
        var products: MutableSet<Product> = mutableSetOf(),
) : Serializable {

    class Product(
            val id: Long,
            var amount: Int
    ) : Serializable

}