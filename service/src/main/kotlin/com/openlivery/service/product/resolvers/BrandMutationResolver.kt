package com.openlivery.service.product.resolvers

import com.openlivery.service.common.domain.entity.Authority
import com.openlivery.service.product.domain.input.InsertBrandInput
import com.openlivery.service.product.domain.input.UpdateBrandInput
import com.openlivery.service.product.domain.model.BrandModel
import com.openlivery.service.product.service.ProductManipulationService
import graphql.kickstart.tools.GraphQLMutationResolver
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component

@Component
class BrandMutationResolver(
        private val productManipulationService: ProductManipulationService
) : GraphQLMutationResolver {

    @PreAuthorize("hasAuthority('${Authority.CREATE_BRAND}')")
    fun insertBrand(input: InsertBrandInput): BrandModel {
        return productManipulationService.insertBrand(input)
                .let { BrandModel.from(it) }
    }

    @PreAuthorize("hasAuthority('${Authority.UPDATE_BRAND}')")
    fun updateBrand(input: UpdateBrandInput): BrandModel {
        return productManipulationService.updateBrand(input)
                .let { BrandModel.from(it) }
    }

    @PreAuthorize("hasAuthority('${Authority.UPDATE_BRAND}')")
    fun disableBrand(brandId: Long): BrandModel {
        return productManipulationService.disableBrand(brandId)
                .let { BrandModel.from(it) }
    }

    @PreAuthorize("hasAuthority('${Authority.UPDATE_BRAND}')")
    fun enableBrand(brandId: Long): BrandModel {
        return productManipulationService.enableBrand(brandId)
                .let { BrandModel.from(it) }
    }

    @PreAuthorize("hasAuthority('${Authority.DELETE_BRAND}')")
    fun deleteBrand(brandId: Long): Boolean {
        productManipulationService.deleteBrand(brandId)
        return true
    }

}