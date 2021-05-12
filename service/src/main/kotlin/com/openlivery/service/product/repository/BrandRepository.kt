package com.openlivery.service.product.repository

import com.openlivery.service.product.domain.Brand
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BrandRepository : JpaRepository<Brand, Long> {

}