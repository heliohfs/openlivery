package com.openlivery.service.product.resolver

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.openlivery.service.product.domain.Category
import com.openlivery.service.product.domain.CategoryInput
import com.openlivery.service.product.service.CategoryService
import com.openlivery.service.product.service.ProductService
import org.springframework.stereotype.Component

@Component
class CategoryMutationResolver(
        val service: CategoryService,
        val productService: ProductService
) : GraphQLMutationResolver {

    fun upsertCategory(categoryInput: CategoryInput): Category {
        val products = categoryInput.productsIds?.let {
            productService.findAllByIdIn(it)
        }
        val category = categoryInput.toCategory(products)
        return service.save(category)
    }

    fun deleteCategoryById(id: Long): Boolean {
        service.deleteById(id)
        return true
    }

}