package com.openlivery.service.common.repository

import com.openlivery.service.common.domain.entity.AppliedDiscount
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AppliedDiscountRepository: JpaRepository<AppliedDiscount, AppliedDiscount.Key> {

    fun findAppliedDiscountByCustomerDataIdentityNumberAndDiscountId(identityNumber: String, discountId: Long): Optional<AppliedDiscount>

}