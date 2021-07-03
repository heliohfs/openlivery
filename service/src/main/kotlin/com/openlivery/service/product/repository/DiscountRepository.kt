package com.openlivery.service.product.repository

import com.openlivery.service.product.domain.entity.Discount
import com.openlivery.service.product.domain.enums.DiscountAccess
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface DiscountRepository: JpaRepository<Discount, Long> {

    fun findAllByCouponCodeAndCouponActiveIsTrueAndActiveIsTrue(couponCode: String): List<Discount>

}