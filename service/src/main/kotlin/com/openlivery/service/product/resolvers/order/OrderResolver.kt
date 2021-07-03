package com.openlivery.service.product.resolvers.order

import com.openlivery.service.common.domain.model.CustomerDataModel
import com.openlivery.service.common.repository.AddressRepository
import com.openlivery.service.product.domain.model.OrderModel
import com.openlivery.service.product.domain.model.OrderProductModel
import com.openlivery.service.product.repository.OrderProductRepository
import com.openlivery.service.product.repository.OrderRepository
import com.openlivery.service.product.service.OrderService
import graphql.kickstart.tools.GraphQLResolver
import org.springframework.stereotype.Component

@Component
class OrderResolver(
        private val orderService: OrderService
) : GraphQLResolver<OrderModel> {

    fun customerData(orderModel: OrderModel) =
            orderService.findCustomerDataByOrderId(orderModel.id)
                    .orElseThrow { error("") }
                    .let { CustomerDataModel.from(it) }

    fun orderProducts(orderModel: OrderModel) =
            orderService.findOrderProductsByOrderId(orderModel.id)
                    .map { OrderProductModel.from(it) }

}