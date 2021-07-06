package com.openlivery.service.product.repository

import com.openlivery.service.common.domain.entity.Customer
import com.openlivery.service.product.domain.entity.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.math.BigDecimal

@Repository
interface DiscountRepository: JpaRepository<Discount, Long> {

    @Query("""
        from Discount discount
            join fetch discount.campaign campaign
            left join discount.claimRule rule
            left join discount.claims claim
            left join discount.coupon coupon
        where discount.active = true and campaign.active = true and
            (coupon is null or (coupon.code = :couponCode and coupon.active = true)) and
            (:customer is null or claim.customerIdentityNumber = :#{#customer?.data?.identityNumber}) and
            (rule is not null or current_timestamp between campaign.startDateTime and campaign.endDateTime) and
            (rule is null or (
                (rule.requiresAuth is false or :customer is not null) and 
                (rule.inBetweenCampaignPeriod = false or current_timestamp between campaign.startDateTime and campaign.endDateTime) and
                (rule.claimLimit is null or rule.claimCount < rule.claimLimit) and 
                (rule.claimLimitByUser is null or (:customer is not null and claim.count < rule.claimLimitByUser)) and
                (:orderValue is null or rule.orderValueAtLeast is null or :orderValue >= rule.orderValueAtLeast) and
                (:orderValue is null or rule.orderValueUpTo is null or :orderValue <= rule.orderValueUpTo) and
                (:deliveryFee is null or rule.deliveryFeeAtLeast is null or :deliveryFee >= rule.deliveryFeeAtLeast) and
                (:deliveryFee is null or rule.deliveryFeeUpTo is null or :deliveryFee <= rule.deliveryFeeUpTo)
            ))
    """)
    fun findAllAvailableDiscounts(
            customer: Customer? = null,
            orderValue: BigDecimal? = null,
            deliveryFee: BigDecimal? = null,
            couponCode: String? = null
    ): List<Discount>

}