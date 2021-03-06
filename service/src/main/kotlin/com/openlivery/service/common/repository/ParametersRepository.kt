package com.openlivery.service.common.repository

import com.openlivery.service.common.domain.entity.Parameters
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ParametersRepository: JpaRepository<Parameters, Short>
