package com.openlivery.service.order.repository

import com.openlivery.service.common.repository.ReadOnlyRepository
import com.openlivery.service.order.domain.Coupon
import org.springframework.stereotype.Repository
import java.util.*

@Repository("OrderCouponRepository")
interface CouponRepository: ReadOnlyRepository<Coupon, Long> {

    fun findOneByCode(code: String): Optional<Coupon>

}