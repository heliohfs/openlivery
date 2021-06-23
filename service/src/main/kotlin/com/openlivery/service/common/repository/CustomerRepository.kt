package com.openlivery.service.common.repository

import com.openlivery.service.common.domain.entity.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CustomerRepository : JpaRepository<Customer, Long> {

    fun findByOauthIdAndActiveIsTrue(oauthId: String): Optional<Customer>

}