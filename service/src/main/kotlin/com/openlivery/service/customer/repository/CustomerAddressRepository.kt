package com.openlivery.service.customer.repository

import com.openlivery.service.customer.domain.CustomerAddress
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerAddressRepository : JpaRepository<CustomerAddress, Long> {
}