package com.openlivery.service.product.repository

import com.openlivery.service.product.domain.entity.Brand
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface BrandRepository : PagingAndSortingRepository<Brand, Long> {

}