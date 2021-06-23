package com.openlivery.service.product.repository

import com.openlivery.service.product.domain.entity.Campaign
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CampaignRepository: JpaRepository<Campaign, Long> {
}