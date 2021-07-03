package com.openlivery.service.product.service

import com.openlivery.service.common.domain.entity.Customer
import com.openlivery.service.common.domain.entity.CustomerDiscountClaim
import com.openlivery.service.common.repository.CustomerDiscountClaimRepository
import com.openlivery.service.product.domain.entity.BaseDiscount
import com.openlivery.service.product.domain.entity.Discount
import com.openlivery.service.product.repository.DiscountRepository
import com.openlivery.service.product.repository.UnderlyingDiscountRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit

@Service
@Transactional
class DiscountService(
        private val discountRepository: DiscountRepository,
        private val underlyingDiscountRepository: UnderlyingDiscountRepository,
        private val customerDiscountClaimRepository: CustomerDiscountClaimRepository
) {

    fun findMatchingUnderlyingDiscounts(
            orderValue: BigDecimal,
            deliveryFee: BigDecimal?,
            customer: Customer?
    ) = underlyingDiscountRepository.findAll()
            .filter { validateDiscount(it, orderValue, deliveryFee, customer) }

    fun findMatchingDiscountsByCouponCode(
            couponCode: String,
            orderValue: BigDecimal,
            deliveryFee: BigDecimal?,
            customer: Customer?
    ) = discountRepository.findAllByCouponCodeAndCouponActiveIsTrueAndActiveIsTrue(couponCode)
            .filter { validateDiscount(it, orderValue, deliveryFee, customer) }

    fun validateDiscount(discount: BaseDiscount, orderValue: BigDecimal, deliveryFee: BigDecimal?, customer: Customer?) =
            discount.claimRule?.run {
                val isAnonymous = customer == null

                val claimCount = customer?.let { customer ->
                    customerDiscountClaimRepository.findById(CustomerDiscountClaim.Key(customer.data.identityNumber, base.id))
                            .map { claim -> claim.count }
                            .orElse(0)
                } ?: 0

                val customerRegistrationDate = customer?.base?.createdDateTime

                (!requiresAuth || !isAnonymous) &&
                        orderValueUpTo?.let { orderValue.compareTo(it) < 1 } ?: true &&
                        orderValueAtLeast?.let { orderValue.compareTo(it) > -1 } ?: true &&
                        deliveryFeeUpTo?.let { deliveryFee != null && deliveryFee.compareTo(it) < 1 } ?: true &&
                        deliveryFeeAtLeast?.let { deliveryFee != null && deliveryFee.compareTo(it) > -1 } ?: true &&
                        claimLimitByUser?.let { !isAnonymous && claimCount < it } ?: true &&
                        maxDaysSinceRegistrationDate?.let {
                            if (isAnonymous) false
                            else ChronoUnit.DAYS.between(LocalDateTime.now(ZoneOffset.UTC), customerRegistrationDate) <= it
                        } ?: true
            } ?: true


}