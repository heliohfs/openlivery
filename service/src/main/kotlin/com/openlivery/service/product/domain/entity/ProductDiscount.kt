package com.openlivery.service.product.domain.entity

import com.openlivery.service.product.domain.enums.DiscountType
import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "product_discount")
@PrimaryKeyJoinColumn(name = "discount_id")
class ProductDiscount(
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "product_id")
        val product: Product,
        campaign: Campaign,
        kind: DiscountType,
        discount: BigDecimal,
        claimRule: ClaimRule?,
        coupon: Coupon? = null
) : Discount(
        campaign = campaign,
        kind = kind,
        discount = discount,
        claimRule = claimRule,
        coupon = coupon
)