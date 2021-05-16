package com.openlivery.service.customer.repository

import com.openlivery.service.customer.domain.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CustomerRepository : JpaRepository<Customer, Long> {

    fun findByOauthIdAndActiveIsTrue(oauthId: String): Optional<Customer>

}