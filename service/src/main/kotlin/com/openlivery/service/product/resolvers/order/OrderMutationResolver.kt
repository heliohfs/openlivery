package com.openlivery.service.product.resolvers.order

import com.openlivery.service.common.auth.AuthProvider
import com.openlivery.service.common.domain.entity.Authority
import com.openlivery.service.common.domain.entity.Customer
import com.openlivery.service.product.domain.model.AnonymousOrderInfoModel
import com.openlivery.service.product.domain.model.OrderModel
import com.openlivery.service.product.domain.model.PlaceAnonymousOrderInput
import com.openlivery.service.product.service.OrderService
import graphql.kickstart.tools.GraphQLMutationResolver
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component

@Component
class OrderMutationResolver(
        private val auth: AuthProvider,
        private val orderService: OrderService
) : GraphQLMutationResolver {

    @PreAuthorize("permitAll()")
    fun placeAnonymousOrder(input: PlaceAnonymousOrderInput) =
            orderService.createAnonymousOrder(
                    cartId = auth.id,
                    customerName = input.completeName,
                    phoneNumber = input.phoneNumber,
                    customerIdentityNumber = input.identityNumber,
                    notes = input.notes
            ).let { AnonymousOrderInfoModel.from(it) }

    @PreAuthorize("hasAuthority('${Authority.CREATE_ORDER}')")
    fun placeOrder(notes: String?): OrderModel {
        if (auth.user !is Customer) throw error("")
        val customer = auth.user as Customer
        return orderService.createAuthenticatedOrder(auth.id, customer.data, notes)
                .let { OrderModel.from(it) }
    }

}