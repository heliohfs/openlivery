package com.openlivery.service.cart.domain

import org.springframework.data.redis.core.RedisHash
import java.io.Serializable

@RedisHash("Cart")
class Cart(
        val id: Long,
        val products: MutableSet<CartCatalogProduct> = mutableSetOf()
): Serializable

