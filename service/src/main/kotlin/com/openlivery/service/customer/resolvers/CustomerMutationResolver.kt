package com.openlivery.service.customer.resolvers

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.openlivery.service.common.auth.impl.AuthenticationFacade
import com.openlivery.service.customer.domain.Customer
import com.openlivery.service.customer.domain.dto.CustomerRegistrationInput
import com.openlivery.service.customer.service.CustomerService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class CustomerMutationResolver(
        val auth: AuthenticationFacade,
        val customerService: CustomerService
) : GraphQLMutationResolver {


    @PreAuthorize("isAuthenticated()")
    @Transactional
    fun registerCustomer(input: CustomerRegistrationInput): Customer {
        val oauthId = auth.authentication.name
        return input.toCustomer(oauthId).also {
            val customer = customerService.save(it)
            customer.addresses.add(customer.defaultAddress!!)
            customerService.save(customer)
        }
    }

    @PreAuthorize("isAuthenticated()")
    fun disableCustomer(customerId: Long): Boolean {
        return true
    }
}