package com.openlivery.service.order.service

import com.openlivery.service.order.domain.Coupon
import com.openlivery.service.order.repository.CouponRepository
import org.springframework.stereotype.Service
import java.util.*

@Service("OrderCouponService")
class CouponService(
        private val repository: CouponRepository
) {

    fun findByCode(code: String): Optional<Coupon> {
        return repository.findOneByCode(code)
    }

}