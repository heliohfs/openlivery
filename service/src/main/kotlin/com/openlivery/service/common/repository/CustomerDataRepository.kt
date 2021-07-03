package com.openlivery.service.common.repository

import com.openlivery.service.common.domain.entity.CustomerData
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CustomerDataRepository: JpaRepository<CustomerData, Long> {

    fun findByIdentityNumber(identityNumber: String): Optional<CustomerData>

    fun findByOrdersPlacedId(orderId: Long): Optional<CustomerData>
}