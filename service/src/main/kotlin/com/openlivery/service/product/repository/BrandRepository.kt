package com.openlivery.service.product.repository

import com.openlivery.service.product.domain.entity.Brand
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface BrandRepository : JpaRepository<Brand, Long> {

    fun findByProductsId(productId: Long): Optional<Brand>

}