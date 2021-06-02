package com.openlivery.service.order.resolver

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.openlivery.service.common.auth.IAuthenticationFacade
import com.openlivery.service.common.domain.Address
import com.openlivery.service.order.domain.Order
import com.openlivery.service.order.domain.OrderCustomer
import com.openlivery.service.order.domain.OrderCustomerData
import com.openlivery.service.order.domain.OrderProduct
import com.openlivery.service.order.domain.dto.OrderProductInput
import com.openlivery.service.order.domain.dto.PlaceAnonymousOrderInput
import com.openlivery.service.order.domain.dto.PlaceOrderInput
import com.openlivery.service.order.service.OrderCatalogProductService
import com.openlivery.service.order.service.OrderService
import org.apache.commons.lang3.tuple.MutablePair
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Component
class OrderMutationResolver(
        val orderService: OrderService,
        val orderCatalogProductService: OrderCatalogProductService,
        val auth: IAuthenticationFacade
) : GraphQLMutationResolver {

    @PreAuthorize("hasAuthority('place_order')")
    @Transactional
    fun placeOrder(input: PlaceOrderInput): Boolean {
        val customer = auth.user as OrderCustomer

        val order = customer.defaultAddress?.let { address ->
            input.toOrder(
                    address = address,
                    customerData = customer.data)
                    .run { orderService.save(this) }
        } ?: throw error("User has no default address")

        val (orderProducts, totalValue) = order.id?.let { orderId ->
            getOrderProductsAndTotalValue(orderId, input.products)
        } ?: throw error("")

        order.orderProducts = orderProducts
        order.orderValue = totalValue

        orderService.save(order)

        return true
    }

    @PreAuthorize("isAnonymous()")
    @Transactional
    fun placeAnonymousOrder(input: PlaceAnonymousOrderInput): Boolean {
        val address = Address(
                streetName = input.streetName,
                streetNumber = input.streetNumber,
                cityName = input.cityName,
                governingDistrict = input.governingDistrict,
                country = input.country,
                additionalInfo = input.additionalInfo
        )

        val customerData = OrderCustomerData(
                completeName = input.completeName,
                phoneNumber = input.phoneNumber
        )

        val order = input.toOrder(
                address = address,
                customerData = customerData,
        ).run { orderService.save(this) }

        val (orderProducts, totalValue) = order.id?.let { orderId ->
            getOrderProductsAndTotalValue(orderId, input.products)
        } ?: throw error("")

        order.orderProducts = orderProducts
        order.orderValue = totalValue

        orderService.save(order)

        return true
    }

    private fun getOrderProductsAndTotalValue(orderId: Long, products: List<OrderProductInput>): MutablePair<MutableSet<OrderProduct>, BigDecimal> {
        return products.fold(MutablePair(mutableSetOf(), BigDecimal.ZERO))
        { acc, it ->
            val catalogProduct = orderCatalogProductService.findById(it.productId)
                    .orElseThrow { error("Product not found") }
            val orderProduct = OrderProduct(orderId = orderId, productId = it.productId, amount = it.amount)
            acc.left.add(orderProduct)
            val price = catalogProduct.finalPrice.multiply(BigDecimal(it.amount))
            acc.right = acc.right.add(price)
            acc
        }
    }

}