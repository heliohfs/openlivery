package com.openlivery.service.product.resolver

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.openlivery.service.product.domain.Category
import com.openlivery.service.product.service.CategoryService
import org.springframework.stereotype.Component

@Component
class CategoryQueryResolver(
        val categoryService: CategoryService
) : GraphQLQueryResolver {

    fun categories(): List<Category> {
        return categoryService.findAll()
    }

    fun categoryById(id: Long): Category {
        return categoryService.findById(id)
                .orElseThrow { error("Category not found") }
    }

}