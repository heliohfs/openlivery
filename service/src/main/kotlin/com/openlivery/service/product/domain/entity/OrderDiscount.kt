package com.openlivery.service.product.domain.entity

import com.openlivery.service.product.domain.enums.DiscountType
import org.apache.commons.lang3.mutable.Mutable
import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "order_discount")
@PrimaryKeyJoinColumn(name = "discount_id")
class OrderDiscount(
        campaign: Campaign,
        kind: DiscountType,
        discount: BigDecimal,
        claimRule: ClaimRule?,
        coupon: Coupon? = null,

        @Column(name = "max_discount_value")
        var maxDiscountValue: BigDecimal? = null,

        @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable(name = "order_discount_valid_product",
                joinColumns = [JoinColumn(name = "order_discount_id")],
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