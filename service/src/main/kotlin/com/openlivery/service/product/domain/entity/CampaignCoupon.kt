package com.openlivery.service.product.domain.entity

import javax.persistence.*

@Entity
@Table(name = "campaign_coupon")
open class CampaignCoupon(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        @ManyToOne
        @JoinColumn(name = "campaign_id")
        var campaign: Campaign,

        @Column(name = "code")
        var code: String,

        @Column(name = "active")
        var active: Boolean = true,

        @Column(name = "auth_required")
        var authRequired: Boolean = false,

        @Column(name = "application_count")
        var applicationCount: Long = 0
) {

    @Column(name = "application_limit_by_user")
    var applicationLimitByUser: Long? = null

    @Column(name = "application_limit")
    var applicationLimit: Long? = null

}