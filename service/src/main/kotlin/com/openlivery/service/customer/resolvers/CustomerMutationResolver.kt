package com.openlivery.service.customer.resolvers

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.openlivery.service.customer.auth.IAuthenticationFacade
import com.openlivery.service.customer.auth.impl.AuthenticationFacade
import com.openlivery.service.customer.domain.Customer
import com.openlivery.service.customer.domain.CustomerAddress
import com.openlivery.service.customer.domain.CustomerAddressInput
import com.openlivery.service.customer.domain.CustomerRegistrationInput
import com.openlivery.service.customer.service.CustomerAddressService
import com.openlivery.service.customer.service.CustomerService
import org.springframework.stereotype.Component

@Component
class CustomerMutationResolver(
        val service: CustomerService,
        val customerAddressService: CustomerAddressService,
        val authenticationFacade: IAuthenticationFacade
) : GraphQLMutationResolver {

    fun registerCustomer(customerRegistrationInput: CustomerRegistrationInput): Customer {
        authenticationFacade.getCurrentCustomer()
                .ifPresent { throw error("User is already registered") }

        return Customer(
                completeName = customerRegistrationInput.completeName,
                phoneNumber = customerRegistrationInput.phoneNumber,
        ).let {
            it.oauthId = authenticationFacade.getAuthentication().name
            it.refCode = "WIP"
            service.save(it)
        }
    }

    fun upsertCustomerAddress(customerAddressInput: CustomerAddressInput): List<CustomerAddress> {
        val customer = authenticationFacade.getCurrentCustomer()
                .orElseThrow { error("User is not logged in") }

        val address = customerAddressInput.id?.let {
            customerAddressService.findById(it)
                    .orElseThrow { error("Address not found") }
        } ?: customerAddressInput.toCustomerAddress()
                .also {
                    it.customer = customer
                }

        customerAddressService.save(address)

        return listOf()
    }

    fun disableCustomer(customerId: Long): Boolean {
        return true
    }

    fun removeCustomerAddress(customerAddressId: Long): Boolean {

        return true
    }
}