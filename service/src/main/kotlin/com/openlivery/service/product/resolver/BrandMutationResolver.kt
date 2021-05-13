package com.openlivery.service.product.resolver

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.openlivery.service.product.domain.Brand
import com.openlivery.service.product.domain.BrandInput
import com.openlivery.service.product.service.BrandService
import com.openlivery.service.product.service.ProductService
import org.springframework.stereotype.Component

@Component
class BrandMutationResolver(
        val service: BrandService,
        val productService: ProductService
) : GraphQLMutationResolver {

    fun upsertBrand(brandInput: BrandInput): Brand {
        val products = brandInput.productsIds?.let {
            productService.findAllByIdIn(it)
        }
        val brand = brandInput.toBrand(products)
        return service.save(brand)
    }

    fun deleteBrandById(id: Long): Boolean {
        service.deleteById(id)
        return true
    }

}