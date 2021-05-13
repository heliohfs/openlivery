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

    fun findAllByIdIn(ids: List<Long>): List<Product> {
        return repository.findAllByIdIn(ids)
    }

    fun save(product: Product): Product {
        return repository.save(product)
    }

    fun deleteById(id: Long) {
        repository.deleteById(id)
    }

}