package com.openlivery.service.product.domain.entity

import org.springframework.data.redis.core.RedisHash
import java.io.Serializable

@RedisHash
data class Cart(
        val id: String,
) : Serializable {

    var appliedCoupon: String? = null

    var products: MutableSet<CartProduct> = mutableSetOf()

}