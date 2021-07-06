package com.openlivery.service.product.service

import com.openlivery.service.common.domain.entity.Address
import com.openlivery.service.common.domain.entity.CustomerData
import com.openlivery.service.common.repository.AddressRepository
import com.openlivery.service.common.repository.CustomerDataRepository
import com.openlivery.service.common.system.SystemParameters
import com.openlivery.service.product.domain.entity.Cart
import com.openlivery.service.product.domain.entity.Order
import com.openlivery.service.product.domain.entity.OrderProduct
import com.openlivery.service.product.domain.enums.OrderStatus
import com.openlivery.service.product.repository.OrderProductRepository
import com.openlivery.service.product.repository.OrderRepository
import com.openlivery.service.product.repository.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.util.*

@Service
@Transactional
class OrderService(
        private val systemParameters: SystemParameters,
        private val productRepository: ProductRepository,
        private val orderRepository: OrderRepository,
        private val orderProductRepository: OrderProductRepository,
        private val cartService: CartService,
        private val addressRepository: AddressRepository,
        private val customerDataRepository: CustomerDataRepository
) {

    fun findOrderProductsByOrderId(orderId: Long): List<OrderProduct> {
        return orderProductRepository.findAllByOrderId(orderId)
    }

    fun findCustomerDataByOrderId(orderId: Long): Optional<CustomerData> {
        return customerDataRepository.findByOrdersPlacedId(orderId)
    }

    fun findOrderByCode(code: String) =
            orderRepository.findByOrderCode(code)

    fun createAnonymousOrder(cartId: String, customerName: String, phoneNumber: String, customerIdentityNumber: String, notes: String?): Order {
        val cart = cartService.getTransientCart(cartId, null)
                .takeIf { it.orderingAvailable } ?: throw error("")
        val customerData = customerDataRepository.findByIdentityNumber(customerIdentityNumber)
                .orElseGet { customerDataRepository.save(CustomerData(customerName, phoneNumber, customerIdentityNumber)) }
        return createOrder(cart, customerData, notes)
    }

    fun createAuthenticatedOrder(cartId: String, customerData: CustomerData, notes: String?): Order {
        val cart = cartService.getTransientCart(cartId, null)
                .takeIf { it.orderingAvailable } ?: throw error("")
        return createOrder(cart, customerData, notes)
    }

    fun createOrder(cart: Cart, customerData: CustomerData, notes: String?): Order {
        if (!cart.orderingAvailable) throw error("")

        val address = cart.deliveryAddress?.run {
            Address(streetName = streetName,
                    streetNumber = streetNumber,
                    cityName = cityName,
                    governingDistrict = governingDistrict,
                    country = country,
                    additionalInfo = additionalInfo,
                    latitude = latitude,
                    longitude = longitude)
        }?.run(addressRepository::save) ?: throw error("")

        return Order(address, notes, customerData, generateCode())
                .apply {
                    deliveryFee = cart.finalDeliveryFee ?: BigDecimal.ZERO
                    status = OrderStatus.PLACED
                    couponApplied = cart.couponApplied
                }
                .run(orderRepository::save)
                .apply {
                    orderProducts = cart.products.mapTo(mutableSetOf()) {
                        productRepository.findById(it.id)
                                .orElseThrow { error("") }
                                .let { product ->
                                    OrderProduct(
                                            order = this,
                                            product = product,
                                            amount = it.amount,
                                            price = it.finalPrice
                                    )
                                }
                    }
                }
                .run(orderRepository::save)
                .apply { cartService.clearCart(cart.id) }
    }

    fun findOrderById(id: Long): Optional<Order> {
        TODO("")
    }

    fun findAllOrdersByIdentityNumber(): List<Order> {
        TODO("")
    }

    private fun generateCode(): String {
        val allowedChars = ('A'..'Z') + ('0'..'9') + ('a'..'z')
        var code: String?
        var tries = 0
        do {
            tries++
            code = (1..8).map { allowedChars.random() }.joinToString("")
            orderRepository.findByOrderCode(code)
                    .ifPresent { order ->
                        if (order.codeAvailable) {
                            order.orderCode = null
                            orderRepository.save(order)
                        } else code = null
                    }
        } while (code == null && tries < 10)
        if (code == null) throw error("")
        return code!!
    }

}