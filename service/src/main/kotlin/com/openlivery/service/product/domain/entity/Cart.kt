package com.openlivery.service.product.domain.entity

import org.springframework.data.redis.core.RedisHash
import java.io.Serializable
import java.math.BigDecimal

@RedisHash
data class Cart(
        val id: String,
        var products: MutableSet<CartProduct> = hashSetOf(),
        var deliveryAddress: CartDeliveryAddress? = null,
        var couponApplied: String? = null
) : Serializable {

    val orderValue: BigDecimal
        get() = products.fold(BigDecimal.ZERO) { total, product ->
            total.add(product.finalPrice.multiply(BigDecimal(product.amount)))
        }

    val finalOrderValue: BigDecimal
        get() = orderDiscount?.applyTo(orderValue) ?: orderValue

    val finalDeliveryFee: BigDecimal?
        get() = deliveryFee?.let { deliveryFeeDiscount?.applyTo(it) }

    val finalValue: BigDecimal?
        get() = finalDeliveryFee?.add(finalOrderValue)

    @Transient
    var deliveryFee: BigDecimal? = null

    @Transient
    var orderDiscount: OrderDiscount? = null

    @Transient
    var deliveryFeeDiscount: DeliveryFeeDiscount? = null

    @Transient
    var orderingAvailable: Boolean = false

}
