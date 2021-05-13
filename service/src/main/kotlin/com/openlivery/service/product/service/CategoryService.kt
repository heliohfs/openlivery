package com.openlivery.service.product.service

import com.openlivery.service.product.domain.Category
import com.openlivery.service.product.repository.CategoryRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class CategoryService(
        val repository: CategoryRepository
) {

    fun findById(id: Long): Optional<Category> {
        return repository.findById(id)
    }

    fun findAll(): List<Category> {
        return repository.findAll()
    }

    fun save(category: Category): Category {
        return repository.save(category)
    }

    fun findAllByIdIn(ids: List<Long>): List<Category> {
        return repository.findAllByIdIn(ids)
    }

    fun deleteById(id: Long) {
        repository.deleteById(id)
    }

}