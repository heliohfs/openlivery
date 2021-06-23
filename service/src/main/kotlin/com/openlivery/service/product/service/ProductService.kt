package com.openlivery.service.product.service

import com.openlivery.service.product.domain.entity.Brand
import com.openlivery.service.product.domain.entity.CatalogProduct
import com.openlivery.service.product.domain.entity.Category
import com.openlivery.service.product.domain.entity.Product
import com.openlivery.service.product.repository.BrandRepository
import com.openlivery.service.product.repository.CatalogProductRepository
import com.openlivery.service.product.repository.CategoryRepository
import com.openlivery.service.product.repository.ProductRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProductService(
        private val productRepository: ProductRepository,
        private val brandRepository: BrandRepository,
        private val categoryRepository: CategoryRepository,
        private val catalogProductRepository: CatalogProductRepository
) {

    fun findAllProducts(): List<Product> {
        return productRepository.findAll()
    }

    fun findProductsByCategoryId(categoryId: Long): List<Product> {
        return productRepository.findByCategoriesId(categoryId)
    }

    fun findAllBrands(): List<Brand> {
        return brandRepository.findAll()
    }

    fun findBrandByProductId(productId: Long): Optional<Brand> {
        return brandRepository.findByProductsId(productId)
    }

    fun findCategoriesByProductId(productId: Long): List<Category> {
        return categoryRepository.findByProductsId(productId)
    }

    fun findAllCategories(): List<Category> {
        return categoryRepository.findAll()
    }

    fun findAllCatalogProducts(): List<CatalogProduct> {
        return catalogProductRepository.findAll()
    }

}