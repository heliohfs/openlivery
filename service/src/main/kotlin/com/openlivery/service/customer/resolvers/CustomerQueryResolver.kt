package com.openlivery.service.customer.resolvers

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.openlivery.service.common.auth.impl.AuthenticationFacade
import com.openlivery.service.customer.domain.Customer
import org.springframework.stereotype.Component

@Component
class CustomerQueryResolver(
        val authenticationFacade: AuthenticationFacade
): GraphQLQueryResolver {

    fun customerProfile(): Customer {
        return authenticationFacade.getCurrentCustomer()
                .orElseThrow { error("User is not registered") }
    }

}