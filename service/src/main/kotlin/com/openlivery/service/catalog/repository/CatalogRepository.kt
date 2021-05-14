package com.openlivery.service.catalog.repository

import com.openlivery.service.catalog.domain.ProductCatalogItem
import com.openlivery.service.common.repository.ReadOnlyPagingAndSortingRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CatalogRepository : ReadOnlyPagingAndSortingRepository<ProductCatalogItem, Long> {

    fun findAllByCategoriesCategoryName(categoryName: String): List<ProductCatalogItem>

}