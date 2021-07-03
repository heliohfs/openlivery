package com.openlivery.service.product.domain.entity

import com.openlivery.service.common.exception.IllegalEntityException
import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "claim_rule")
class DiscountClaimRule(
        @Column(name = "claim_limit")
        var claimLimit: Int? = null,

        @Column(name = "claim_limit_by_user")
        var claimLimitByUser: Int? = null,

        @Column(name = "order_value_at_least")
        var orderValueAtLeast: BigDecimal? = null,

        @Column(name = "order_value_up_to")
        var orderValueUpTo: BigDecimal? = null,

        @Column(name = "delivery_fee_at_least")
        var deliveryFeeAtLeast: BigDecimal? = null,

        @Column(name = "delivery_fee_up_to")
        var deliveryFeeUpTo: BigDecimal? = null,

        @Column(name = "in_between_campaign_period")
        var inBetweenCampaignPeriod: Boolean = false,

        @Column(name = "requires_auth")
        var requiresAuth: Boolean = true,

        @Column(name = "max_days_since_registration_date")
        var maxDaysSinceRegistrationDate: Int? = null
) {

    @Id
    val id: Long? = null

    abstract class Base {
        abstract val id: Long
    }

    @Transient
    val base = object : Base() {
        override val id: Long
            get() = this@DiscountClaimRule.id ?: throw IllegalEntityException()
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "claimRule")
    val discounts: Set<Discount> = hashSetOf()

}