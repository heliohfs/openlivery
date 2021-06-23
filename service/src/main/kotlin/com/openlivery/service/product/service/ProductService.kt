package com.openlivery.service.product.service

import com.openlivery.service.product.domain.entity.Brand
import com.openlivery.service.product.domain.entity.Category
import com.openlivery.service.product.domain.entity.Product
import com.openlivery.service.product.repository.BrandRepository
import com.openlivery.service.product.repository.CategoryRepository
import com.openlivery.service.product.repository.ProductRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProductService(
        private val productRepository: ProductRepository,
        private val brandRepository: BrandRepository,
        private val categoryRepository: CategoryRepository
) {

    fun findAll(): List<Product> {
        return productRepository.findAll()
    }

    fun findBrandById(id: Long): Optional<Brand> {
        return brandRepository.findById(id)
    }

    fun findCategoryById(id: Long): Optional<Category> {
        return categoryRepository.findById(id)
    }


}