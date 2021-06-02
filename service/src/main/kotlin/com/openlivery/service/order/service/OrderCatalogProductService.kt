package com.openlivery.service.order.service

import com.openlivery.service.order.domain.OrderCatalogProduct
import com.openlivery.service.order.repository.OrderCatalogProductRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class OrderCatalogProductService(
        val repository: OrderCatalogProductRepository
) {

    fun findAllByIdIn(ids: List<Long>): List<OrderCatalogProduct> {
        return repository.findAllByIdIn(ids)
    }

    fun findById(id: Long): Optional<OrderCatalogProduct> {
        return repository.findById(id)
    }

}