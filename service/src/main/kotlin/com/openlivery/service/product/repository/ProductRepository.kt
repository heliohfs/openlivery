package com.openlivery.service.product.repository

import com.openlivery.service.product.domain.entity.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<Product, Long> {

    fun findByCategoriesId(categoryId: Long): List<Product>

}