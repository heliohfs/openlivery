package com.openlivery.service.product.domain.entity

import com.openlivery.service.common.domain.entity.BaseEntity
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "campaign")
class Campaign(
        @Column(name = "campaign_name")
        var name: String? = null,

        @Column(name = "description")
        var description: String? = null,

        @Column(name = "banner_picture_storage_key")
        var bannerPictureStorageKey: String? = null,

        @Column(name = "start_datetime")
        var startDateTime: LocalDateTime,

        @Column(name = "end_datetime")
        var endDateTime: LocalDateTime? = null
) : BaseEntity()