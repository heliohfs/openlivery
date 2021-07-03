package com.openlivery.service.product.domain.entity

import com.openlivery.service.common.domain.entity.BaseEntity
import com.openlivery.service.product.domain.enums.DiscountApplication
import com.openlivery.service.product.domain.enums.DiscountType
import java.math.BigDecimal
import javax.persistence.*

@MappedSuperclass
abstract class BaseDiscount(
        @Column(name = "apply_to")
        @Enumerated(EnumType.STRING)
        var applyTo: DiscountApplication,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "campaign_id")
        val campaign: Campaign,

        @Column(name = "discount_type")
        @Enumerated(EnumType.STRING)
        var discountType: DiscountType,

        @Column(name = "discount")
        var discount: BigDecimal,

        @Column(name = "max_order_discount_value")
        var maxOrderDiscountValue: BigDecimal? = null,

        @ManyToOne
        @JoinColumn(name = "coupon_code")
        var coupon: Coupon? = null
): BaseEntity() {

    @ManyToOne
    @JoinColumn(name = "claim_rule_id")
    var claimRule: DiscountClaimRule? = null

    @Column(name = "product_id")
    var productId: Long? = null

    @Transient
    var isProductDiscount: Boolean = false
    private set
    get() = applyTo == DiscountApplication.PRODUCT

    @Transient
    var isOrderDiscount: Boolean = false
    private set
    get() = applyTo == DiscountApplication.ORDER

    @Transient
    var isDeliveryFeeDiscount: Boolean = false
    private set
    get() = applyTo == DiscountApplication.DELIVERY_FEE

}