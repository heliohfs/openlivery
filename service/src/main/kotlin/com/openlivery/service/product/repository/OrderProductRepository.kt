package com.openlivery.service.product.repository

import com.openlivery.service.product.domain.entity.OrderProduct
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderProductRepository: JpaRepository<OrderProduct, OrderProduct.Key> {
}