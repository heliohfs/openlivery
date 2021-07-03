package com.openlivery.service.product.resolvers.product

import com.openlivery.service.common.domain.entity.Authority
import com.openlivery.service.product.domain.input.InsertBrandInput
import com.openlivery.service.product.domain.input.UpdateBrandInput
import com.openlivery.service.product.domain.model.BrandModel
import com.openlivery.service.product.service.ProductService
import graphql.kickstart.tools.GraphQLMutationResolver
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component

@Component
class BrandMutationResolver(
        private val productService: ProductService
) : GraphQLMutationResolver {

    @PreAuthorize("hasAuthority('${Authority.CREATE_BRAND}')")
    fun insertBrand(input: InsertBrandInput): BrandModel {
        return productService.insertBrand(input)
                .let { BrandModel.from(it) }
    }

    @PreAuthorize("hasAuthority('${Authority.UPDATE_BRAND}')")
    fun updateBrand(input: UpdateBrandInput): BrandModel {
        return productService.updateBrand(input)
                .let { BrandModel.from(it) }
    }

    @PreAuthorize("hasAuthority('${Authority.UPDATE_BRAND}')")
    fun disableBrand(brandId: Long): BrandModel {
        return productService.disableBrand(brandId)
                .let { BrandModel.from(it) }
    }

    @PreAuthorize("hasAuthority('${Authority.UPDATE_BRAND}')")
    fun enableBrand(brandId: Long): BrandModel {
        return productService.enableBrand(brandId)
                .let { BrandModel.from(it) }
    }

    @PreAuthorize("hasAuthority('${Authority.DELETE_BRAND}')")
    fun deleteBrand(brandId: Long): Boolean {
        productService.deleteBrand(brandId)
        return true
    }

}