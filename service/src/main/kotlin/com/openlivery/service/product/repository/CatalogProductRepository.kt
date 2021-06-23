package com.openlivery.service.product.repository

import com.openlivery.service.common.repository.ReadOnlyPagingAndSortingRepository
import com.openlivery.service.common.repository.ReadOnlyRepository
import com.openlivery.service.product.domain.entity.CatalogProduct
import org.springframework.stereotype.Repository

@Repository
interface CatalogProductRepository : ReadOnlyRepository<CatalogProduct, Long> {

}