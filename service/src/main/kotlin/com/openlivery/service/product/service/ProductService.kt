package com.openlivery.service.product.service

import com.openlivery.service.product.domain.Product
import com.openlivery.service.product.repository.ProductRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProductService(
        val repository: ProductRepository
) {

    fun findById(id: Long): Optional<Product> {
        return repository.findById(id)
    }

    fun findAll(): List<Product> {
        return repository.findAll()
    }

    fun save(product: Product): Product {
        return repository.save(product)
    }
}