package com.openlivery.service.product.service

import com.openlivery.service.product.domain.entity.Brand
import com.openlivery.service.product.domain.entity.Category
import com.openlivery.service.product.domain.entity.Product
import com.openlivery.service.product.domain.input.InsertBrandInput
import com.openlivery.service.product.domain.input.InsertProductInput
import com.openlivery.service.product.domain.input.UpdateBrandInput
import com.openlivery.service.product.domain.input.UpdateProductInput
import com.openlivery.service.product.repository.BrandRepository
import com.openlivery.service.product.repository.CategoryRepository
import com.openlivery.service.product.repository.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import kotlin.collections.HashSet

@Service
@Transactional
class ProductService(
        private val productRepository: ProductRepository,
        private val brandRepository: BrandRepository,
        private val categoryRepository: CategoryRepository,
) {

    fun insertProduct(input: InsertProductInput) =
            input.let { Product(it.name, it.basePrice) }
                    .apply {
                        description = input.description
                        itemCode = input.itemCode
                        brand = input.brandId?.let { id ->
                            brandRepository.findById(id)
                                    .orElseThrow { error("") }
                        }
                        categories = input.categoriesIds?.mapTo(HashSet()) { id ->
                            categoryRepository.findById(id)
                                    .orElseThrow { error("") }
                        } ?: mutableSetOf()
                    }
                    .run { productRepository.save(this) }

    fun updateProduct(input: UpdateProductInput) = input
            .let { productRepository.findById(input.id) }
            .orElseThrow { error("") }
            .apply {
                name = input.name
                basePrice = input.basePrice
                description = input.description
                itemCode = input.itemCode
                brand = input.brandId?.let { id ->
                    brandRepository.findById(id)
                            .orElseThrow { error("") }
                }
                categories = input.categoriesIds?.mapTo(HashSet()) { id ->
                    categoryRepository.findById(id)
                            .orElseThrow { error("") }
                } ?: mutableSetOf()
            }
            .run { productRepository.save(this) }

    fun disableProduct(productId: Long) = productRepository.findById(productId)
            .orElseThrow { error("") }
            .apply { active = false }
            .run { productRepository.save(this) }

    fun enableProduct(productId: Long) = productRepository.findById(productId)
            .orElseThrow { error("") }
            .apply { active = true }
            .run { productRepository.save(this) }

    fun deleteProduct(productId: Long) = productRepository.deleteById(productId)


    fun insertBrand(input: InsertBrandInput) = Brand(input.name)
            .run { brandRepository.save(this) }

    fun updateBrand(input: UpdateBrandInput) = input.id
            .let { brandRepository.findById(it) }
            .orElseThrow { error("") }
            .apply { name = input.name }
            .run { brandRepository.save(this) }

    fun disableBrand(brandId: Long) = brandRepository.findById(brandId)
            .orElseThrow { error("") }
            .apply { active = false }
            .run { brandRepository.save(this) }

    fun enableBrand(brandId: Long) = brandRepository.findById(brandId)
            .orElseThrow { error("") }
            .apply { active = true }
            .run { brandRepository.save(this) }

    fun deleteBrand(brandId: Long) = brandRepository.deleteById(brandId)

    fun findAllProducts(): List<Product> = productRepository.findAll()

    fun findProductsByCategoryId(categoryId: Long): List<Product> = productRepository.findByCategoriesId(categoryId)

    fun findAllBrands(): List<Brand> = brandRepository.findAll()

    fun findBrandByProductId(productId: Long): Optional<Brand> = brandRepository.findByProductsId(productId)

    fun findCategoriesByProductId(productId: Long): List<Category> = categoryRepository.findByProductsId(productId)

    fun findAllCategories(): List<Category> = categoryRepository.findAll()

    fun findProductById(id: Long) = productRepository.findById(id)

    fun findCategoryById(id: Long) = categoryRepository.findById(id)

    fun findBrandById(id: Long) = brandRepository.findById(id)

}