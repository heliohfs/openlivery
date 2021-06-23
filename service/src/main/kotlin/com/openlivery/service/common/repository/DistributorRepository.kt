package com.openlivery.service.common.repository

import com.openlivery.service.common.domain.entity.Distributor
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DistributorRepository: JpaRepository<Distributor, Long> {
}