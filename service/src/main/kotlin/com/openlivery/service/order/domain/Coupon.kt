package com.openlivery.service.order.domain

import org.springframework.data.annotation.Immutable
import java.io.Serializable
import javax.persistence.*

@Entity(name = "OrderCoupon")
@Table(name = "active_coupon")
@Immutable
class Coupon private constructor(
        @Id
        val id: Long,

        @Column(name = "code")
        val code: String,

        @Column(name = "application_limit_by_user")
        val applicationLimitByUser: Long? = null,

        @Column(name = "campaign_id")
        val campaignId: Long,

        @OneToMany(fetch = FetchType.EAGER)
        @JoinColumn(name = "campaign_id", referencedColumnName = "campaign_id")
        val discounts: List<Discount> = mutableListOf(),

        @Column(name = "auth_required")
        val authRequired: Boolean = false,
) : Serializable