package com.openlivery.service.order.service

import com.openlivery.service.order.domain.CustomerCoupon
import com.openlivery.service.order.repository.CustomerCouponRepository
import org.springframework.stereotype.Service
import java.util.*

@Service("OrderCustomerCouponService")
class CustomerCouponService(
        private val repository: CustomerCouponRepository
) {

    fun save(customerCoupon: CustomerCoupon): CustomerCoupon {
        return repository.save(customerCoupon)
    }

    fun findByCustomerIdentityNumberAndCouponCode(customerIdentityNumber: String, couponCode: String): Optional<CustomerCoupon> {
        return repository.findByCustomerIdentityNumberAndCouponCode(customerIdentityNumber, couponCode)
    }

}