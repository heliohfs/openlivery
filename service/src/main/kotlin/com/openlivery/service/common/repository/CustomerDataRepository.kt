package com.openlivery.service.common.repository

import com.openlivery.service.common.domain.entity.CustomerData
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerDataRepository: JpaRepository<CustomerData, Long> {
}