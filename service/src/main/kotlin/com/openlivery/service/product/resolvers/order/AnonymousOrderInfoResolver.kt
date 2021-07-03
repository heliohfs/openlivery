package com.openlivery.service.product.resolvers.order

import com.openlivery.service.product.domain.model.AnonymousOrderInfoModel
import com.openlivery.service.product.domain.model.OrderProductModel
import com.openlivery.service.product.service.OrderService
import graphql.kickstart.tools.GraphQLResolver
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component

@Component
class AnonymousOrderInfoResolver(
        private val orderService: OrderService
) : GraphQLResolver<AnonymousOrderInfoModel> {

    @PreAuthorize("permitAll()")
    fun orderProducts(anonymousOrderInfoModel: AnonymousOrderInfoModel) =
            orderService.findOrderProductsByOrderId(anonymousOrderInfoModel.id)
                    .map { OrderProductModel.from(it) }

}