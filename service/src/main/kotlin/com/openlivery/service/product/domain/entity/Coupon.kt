package com.openlivery.service.product.domain.entity

import javax.persistence.*

@Entity
@Table(name = "coupon")
class Coupon(
        @Id
        @Column(name = "code")
        var code: String,

        @Column(name = "active")
        var active: Boolean = true,

        @Column(name = "application_count")
        var applicationCount: Long = 0,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "campaign_id")
        var campaign: Campaign,

        @Column(name = "application_limit")
        var applicationLimit: Long? = null,

        @Column(name = "application_limit_by_user")
        var applicationLimitByUser: Long? = null,

        @Column(name = "allow_anonymous")
        var allowAnonymous: Boolean = false,

        @OneToMany(fetch = FetchType.LAZY, mappedBy = "coupon")
        var discounts: MutableSet<Discount> = hashSetOf()
)