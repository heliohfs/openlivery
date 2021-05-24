package com.openlivery.service.customer.resolvers

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.openlivery.service.customer.domain.Customer
import org.springframework.stereotype.Component
import org.springframework.security.access.prepost.PreAuthorize
import com.openlivery.service.common.auth.impl.AuthenticationFacade

@Component
class CustomerQueryResolver(
        val auth: AuthenticationFacade
) : GraphQLQueryResolver {

    @PreAuthorize("hasAuthority('')")
    fun customerProfile(): Customer {
        return auth.user as Customer;
    }

}