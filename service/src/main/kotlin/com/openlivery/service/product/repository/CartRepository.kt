package com.openlivery.service.product.repository

import com.openlivery.service.product.domain.entity.Cart
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CartRepository: CrudRepository<Cart, String> {
}