package com.openlivery.service.product.resolver

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.openlivery.service.product.domain.Brand
import com.openlivery.service.product.service.BrandService
import org.springframework.stereotype.Component

@Component
class BrandQueryResolver(
        val brandService: BrandService
) : GraphQLQueryResolver {

    fun brands(): List<Brand> {
        return brandService.findAll()
    }

    fun brandById(id: Long): Brand {
        return brandService.findById(id)
                .orElseThrow { error("Brand not found") }
    }

}