package com.openlivery.service.order.repository

import com.openlivery.service.order.domain.CustomerData
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository("OrderCustomerDataRepository")
interface CustomerDataRepository : JpaRepository<CustomerData, Long> {

    fun findByIdentityNumber(identityNumber: String): Optional<CustomerData>

}