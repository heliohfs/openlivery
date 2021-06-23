package com.openlivery.service.product.domain.entity

import com.openlivery.service.common.domain.entity.BaseEntity
import com.openlivery.service.product.domain.enums.CampaignDiscountStrategy
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "campaign")
class Campaign(
        @Column(name = "campaign_name")
        var campaignName: String,

        @Column(name = "description")
        var description: String,

        @Column(name = "banner_picture_storage_key")
        var bannerPictureStorageKey: String,

        @Column(name = "start_datetime")
        var startDateTime: LocalDateTime,

        @Column(name = "end_datetime")
        var endDateTime: LocalDateTime,

        @Enumerated(EnumType.STRING)
        @Column(name = "discount_strategy")
        var discountStrategy: CampaignDiscountStrategy
): BaseEntity() {
}