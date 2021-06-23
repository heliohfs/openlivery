package com.openlivery.service.common.repository

import com.openlivery.service.common.domain.entity.CustomerCoupon
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerCouponRepository: JpaRepository<CustomerCoupon, CustomerCoupon.Key> {

}