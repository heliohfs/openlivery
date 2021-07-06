package com.openlivery.service.common.repository

import com.openlivery.service.common.domain.entity.CustomerDiscountClaim
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerDiscountClaimRepository: JpaRepository<CustomerDiscountClaim, CustomerDiscountClaim.Key>