package com.openlivery.service.product.domain.entity

import com.openlivery.service.product.domain.enums.DiscountType
import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "delivery_fee_discount")
@PrimaryKeyJoinColumn(name = "discount_id")
class DeliveryFeeDiscount(
        campaign: Campaign,
        kind: DiscountType,
        discount: BigDecimal,
        claimRule: ClaimRule?,
        coupon: Coupon? = null,

        @Column(name = "max_discount_value")
        var maxDiscountValue: BigDecimal? = null,

        @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable(name = "delivery_fee_discount_valid_product",
                joinColumns = [JoinColumn(name = "delivery_fee_discount_id")],
                inverseJoinColumns = [JoinColumn(name = "product_id")]
        )
        var validProducts: MutableSet<Product> = hashSetOf()
) : Discount(
        campaign = campaign,
        kind = kind,
        discount = discount,
        claimRule = claimRule,
        coupon = coupon
)