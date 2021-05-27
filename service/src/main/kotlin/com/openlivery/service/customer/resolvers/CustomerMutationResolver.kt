package com.openlivery.service.customer.resolvers

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.openlivery.service.common.auth.impl.AuthenticationFacade
import com.openlivery.service.common.domain.Authority
import com.openlivery.service.customer.domain.Customer
import com.openlivery.service.customer.domain.dto.CustomerModel
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

    @Transactional
    fun registerCustomer(input: CustomerRegistrationInput): Boolean {
        val oauthId = auth.authentication.name
        input.toCustomer(oauthId).run {
            val customer = customerService.save(this)
            customer.addresses.add(customer.defaultAddress!!)
            customerService.save(customer)
        }
        return true
    }

    fun disableCustomer(customerId: Long): Boolean {
        return true
    }

}