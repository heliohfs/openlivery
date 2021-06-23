package com.openlivery.service.product.repository

import com.openlivery.service.product.domain.entity.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : JpaRepository<Category, Long> {

    fun findByProductsId(productId: Long): List<Category>

}