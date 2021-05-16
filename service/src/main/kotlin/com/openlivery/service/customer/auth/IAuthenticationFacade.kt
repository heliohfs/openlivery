package com.openlivery.service.customer.auth

import com.openlivery.service.customer.domain.Customer
import org.springframework.security.core.Authentication
import java.util.*

interface IAuthenticationFacade {
    fun getAuthentication(): Authentication
    fun getCurrentCustomer(): Optional<Customer>
}