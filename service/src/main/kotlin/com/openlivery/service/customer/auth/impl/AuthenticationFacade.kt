package com.openlivery.service.customer.auth.impl

import com.openlivery.service.customer.auth.IAuthenticationFacade
import com.openlivery.service.customer.domain.Customer
import com.openlivery.service.customer.service.CustomerService
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.util.*

@Component
class AuthenticationFacade(
        val customerService: CustomerService
) : IAuthenticationFacade {

    override fun getAuthentication(): Authentication {
        return SecurityContextHolder.getContext().authentication
    }

    override fun getCurrentCustomer(): Optional<Customer> {
        return customerService.findByOauthId(SecurityContextHolder.getContext().authentication.name)
    }

}