package com.openlivery.service.product.repository

import com.openlivery.service.product.domain.entity.Coupon
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CouponRepository : JpaRepository<Coupon, String> {

    fun findByCodeAndActiveIsTrue(couponCode: String): Optional<Coupon>

}