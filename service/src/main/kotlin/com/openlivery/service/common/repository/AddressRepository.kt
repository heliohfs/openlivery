package com.openlivery.service.common.repository

import com.openlivery.service.common.domain.entity.Address
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AddressRepository: JpaRepository<Address, Long> {
}