package com.openlivery.service.order.repository

import com.openlivery.service.common.repository.ReadOnlyRepository
import com.openlivery.service.order.domain.CatalogProduct
import org.springframework.stereotype.Repository

@Repository("OrderCatalogProductRepository")
interface CatalogProductRepository : ReadOnlyRepository<CatalogProduct, Long> {

    fun findAllByIdIn(ids: List<Long>): List<CatalogProduct>

    fun existsById(id: Long): Boolean

}