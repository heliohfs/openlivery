package com.openlivery.service.product.repository

import com.openlivery.service.product.domain.entity.Coupon
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CouponRepository : JpaRepository<Coupon, String> {

    @Query("""
        from Coupon coupon
            join coupon.campaign campaign
        where coupon.id = :id and 
            coupon.active = true and 
            campaign.active = true and
            current_timestamp between campaign.startDateTime and campaign.endDateTime and
            (coupon.allowAnonymous = true or :isAnonymous = false)
    """)
    fun findAvailableById(id: String, isAnonymous: Boolean): Optional<Coupon>

}