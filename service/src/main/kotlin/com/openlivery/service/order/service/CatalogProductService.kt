package com.openlivery.service.order.service

import com.openlivery.service.order.domain.CatalogProduct
import com.openlivery.service.order.repository.CatalogProductRepository
import org.springframework.stereotype.Service
import java.util.*

@Service("OrderCatalogProductService")
class CatalogProductService(
        val repository: CatalogProductRepository
) {

    fun findAllByIdIn(ids: List<Long>): List<CatalogProduct> {
        return repository.findAllByIdIn(ids)
    }

    fun findById(id: Long): Optional<CatalogProduct> {
        return repository.findById(id)
    }

    fun existsById(id: Long): Boolean {
        return repository.existsById(id)
    }

}