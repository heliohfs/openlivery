package com.openlivery.service.customer.resolvers

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.openlivery.service.common.auth.impl.AuthenticationFacade
import com.openlivery.service.customer.domain.Customer
import com.openlivery.service.customer.domain.dto.CustomerModel
import org.springframework.stereotype.Component

@Component
class CustomerQueryResolver(
        val auth: AuthenticationFacade
) : GraphQLQueryResolver {

    fun customerProfile(): CustomerModel {
        return CustomerModel.from(auth.user as Customer);
    }

}