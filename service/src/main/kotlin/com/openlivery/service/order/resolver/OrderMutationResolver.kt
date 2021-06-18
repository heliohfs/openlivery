package com.openlivery.service.order.resolver

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.openlivery.service.common.auth.IAuthenticationFacade
import com.openlivery.service.common.domain.Address
import com.openlivery.service.common.domain.Authority
import com.openlivery.service.order.domain.Customer
import com.openlivery.service.order.domain.CustomerData
import com.openlivery.service.order.domain.Order
import com.openlivery.service.order.domain.OrderProduct
import com.openlivery.service.order.domain.dto.PlaceAnonymousOrderInput
import com.openlivery.service.order.domain.dto.PlaceOrderInput
import com.openlivery.service.order.service.CartService
import com.openlivery.service.order.service.CustomerDataService
import com.openlivery.service.order.service.OrderService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.util.*

@Component
class OrderMutationResolver(
        val auth: IAuthenticationFacade,
        val orderService: OrderService,
        val cartService: CartService,
        val customerDataService: CustomerDataService
) : GraphQLMutationResolver {

    @PreAuthorize("hasAuthority('${Authority.PLACE_ORDER}')")
    fun placeOrder(input: PlaceOrderInput): Long {
        val cart = cartService.getCart(auth.id)
                .takeIf { it.totalValue > BigDecimal.ZERO } ?: throw error("Cart is empty")

        val user = auth.user as Customer

        val address = Optional.ofNullable(user.defaultAddress)
                .orElseThrow { error("User has no default address") }

        val order = orderService.createOrder(
                totalValue = cart.totalValue,
                deliveryFee = cart.deliveryFee,
                products = cart.products.map { OrderProduct(it.id, it.amount) },
                coupon = cart.couponApplied,
                customerData = user.data,
                address = address,
                notes = input.notes
        )

        cartService.clearCart(auth.id)
        return order.id!!
    }

    @PreAuthorize("isAnonymous()")
    fun placeAnonymousOrder(input: PlaceAnonymousOrderInput): Long {
        val cart = cartService.getCart(auth.id)
                .takeIf { it.totalValue > BigDecimal.ZERO } ?: throw error("Cart is empty")

        val address = Address(
                streetName = input.streetName,
                streetNumber = input.streetNumber,
                cityName = input.cityName,
                governingDistrict = input.governingDistrict,
                country = input.country,
                additionalInfo = input.additionalInfo
        )

        val customerData = customerDataService.findByIdentityNumber(input.identityNumber)
                .orElseGet { CustomerData(input.completeName, input.phoneNumber, input.identityNumber) }

        val order = orderService.createOrder(
                totalValue = cart.totalValue,
                deliveryFee = cart.deliveryFee,
                products = cart.products.map { OrderProduct(it.id, it.amount) },
                coupon = cart.couponApplied,
                customerData = customerData,
                address = address,
                notes = input.notes
        )

        cartService.clearCart(auth.id)
        return order.id!!
    }

}