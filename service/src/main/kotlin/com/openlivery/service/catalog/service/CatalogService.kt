package com.openlivery.service.catalog.service

import com.openlivery.service.catalog.domain.ProductCatalogItem
import com.openlivery.service.catalog.repository.CatalogRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class CatalogService(
        val repository: CatalogRepository
) {

    fun findAll(): List<ProductCatalogItem> {
        return repository.findAll()
    }

    fun findById(id: Long): Optional<ProductCatalogItem> {
        return repository.findById(id)
    }

    fun findAllByCategoryName(categoryName: String): List<ProductCatalogItem> {
        return repository.findAllByCategoriesCategoryName(categoryName)
    }

}