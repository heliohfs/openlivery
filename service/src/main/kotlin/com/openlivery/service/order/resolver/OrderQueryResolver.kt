package com.openlivery.service.order.resolver

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.openlivery.service.common.auth.IAuthenticationFacade
import com.openlivery.service.order.service.OrderCatalogProductService
import com.openlivery.service.order.service.OrderService
import org.springframework.stereotype.Component

@Component
class OrderQueryResolver(
        val orderService: OrderService,
        val orderCatalogProductService: OrderCatalogProductService,
        val auth: IAuthenticationFacade
): GraphQLQueryResolver {


}