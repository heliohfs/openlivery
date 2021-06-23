package com.openlivery.service.product.repository

import com.openlivery.service.product.domain.entity.CampaignCoupon
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CampaignCouponRepository: JpaRepository<CampaignCoupon, Long> {
}