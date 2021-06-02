package com.openlivery.service.order.repository

import com.openlivery.service.common.repository.ReadOnlyRepository
import com.openlivery.service.order.domain.OrderCatalogProduct
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface OrderCatalogProductRepository : ReadOnlyRepository<OrderCatalogProduct, Long> {

    fun findAllByIdIn(ids: List<Long>): List<OrderCatalogProduct>

}