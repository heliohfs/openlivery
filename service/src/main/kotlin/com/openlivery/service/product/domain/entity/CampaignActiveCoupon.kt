package com.openlivery.service.product.domain.entity

import org.springframework.data.annotation.Immutable
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Immutable
@Table(name = "active_coupon")
class CampaignActiveCoupon private constructor(
        id: Long,
        campaign: Campaign,
        code: String,
        active: Boolean,
        authRequired: Boolean,
        applicationCount: Long,
) : CampaignCoupon(
        id,
        campaign,
        code,
        active,
        authRequired,
        applicationCount
)