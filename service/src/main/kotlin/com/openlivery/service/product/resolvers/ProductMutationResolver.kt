package com.openlivery.service.product.resolvers

import com.openlivery.service.common.domain.entity.Authority
import com.openlivery.service.product.domain.input.InsertProductInput
import com.openlivery.service.product.domain.input.UpdateProductInput
import com.openlivery.service.product.domain.model.ProductModel
import com.openlivery.service.product.service.ProductService
import graphql.kickstart.tools.GraphQLMutationResolver
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component

@Component
class ProductMutationResolver(
        private val productService: ProductService,
) : GraphQLMutationResolver {

    @PreAuthorize("hasAuthority('${Authority.CREATE_PRODUCT}')")
    fun insertProduct(input: InsertProductInput): ProductModel {
        return productService.insertProduct(input)
                .let { ProductModel.from(it) }
    }

    @PreAuthorize("hasAuthority('${Authority.UPDATE_PRODUCT}')")
    fun updateProduct(input: UpdateProductInput): ProductModel {
        return productService.updateProduct(input)
                .let { ProductModel.from(it) }
    }

    @PreAuthorize("hasAuthority('${Authority.UPDATE_PRODUCT}')")
    fun disableProduct(productId: Long): ProductModel {
        return productService.disableProduct(productId)
                .let { ProductModel.from(it) }
    }

    @PreAuthorize("hasAuthority('${Authority.UPDATE_PRODUCT}')")
    fun enableProduct(productId: Long): ProductModel {
        return productService.enableProduct(productId)
                .let { ProductModel.from(it) }
    }

    @PreAuthorize("hasAuthority('${Authority.DELETE_PRODUCT}')")
    fun deleteProduct(productId: Long): Boolean {
        productService.deleteProduct(productId)
        return true
    }

}