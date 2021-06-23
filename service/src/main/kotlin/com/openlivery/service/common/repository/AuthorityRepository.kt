package com.openlivery.service.common.repository

import com.openlivery.service.common.domain.entity.Authority
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthorityRepository: JpaRepository<Authority, Long> {
}