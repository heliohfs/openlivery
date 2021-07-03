package com.openlivery.service.product.domain.entity

import com.openlivery.service.product.domain.enums.DiscountType
import org.springframework.data.redis.core.RedisHash
import java.io.Serializable
import java.math.BigDecimal

@RedisHash
data class Cart(
        val id: String,
) : Serializable {

    var products: MutableSet<CartProduct> = hashSetOf()

    var deliveryAddress: CartDeliveryAddress? = null

    var couponApplied: String? = null

    @Transient
    var orderDiscountSource: String? = null

    @Transient
    var orderDiscountId: Long? = null

    @Transient
    var orderDiscountType: DiscountType? = null

    @Transient
    var orderDiscount: BigDecimal? = null

    @Transient
    var orderValue: BigDecimal = BigDecimal.ZERO

    @Transient
    var orderValueSaved: BigDecimal? = null

    @Transient
    var finalOrderValue: BigDecimal = BigDecimal.ZERO

    @Transient
    var orderDiscountApplied: Boolean = false

    @Transient
    var deliveryFeeDiscountSource: String? = null

    @Transient
    var deliveryFeeDiscountId: Long? = null

    @Transient
    var deliveryFeeDiscountType: DiscountType? = null

    @Transient
    var deliveryFeeDiscount: BigDecimal? = null

    @Transient
    var deliveryFee: BigDecimal? = null

    @Transient
    var finalDeliveryFee: BigDecimal? = null

    @Transient
    var deliveryFeeDiscountApplied: Boolean = false

    @Transient
    var finalValue: BigDecimal = BigDecimal.ZERO

    @Transient
    var orderingAvailable: Boolean = false

}
