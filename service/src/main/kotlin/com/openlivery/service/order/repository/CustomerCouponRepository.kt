package com.openlivery.service.order.repository

import com.openlivery.service.order.domain.CustomerCoupon
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository("OrderCustomerCouponRepository")
interface CustomerCouponRepository: JpaRepository<CustomerCoupon, CustomerCoupon.Key> {

    fun findByCustomerIdentityNumberAndCouponCode(customerIdentityNumber: String, couponCode: String): Optional<CustomerCoupon>

}