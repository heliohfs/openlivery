package com.openlivery.service.product.domain.model

import com.openlivery.service.product.domain.entity.Brand

class BrandModel private constructor(
        val id: Long,
        val active: Boolean,
        val name: String,
) {

    companion object {
        fun from(brand: Brand): BrandModel {
            return BrandModel(
                    id = brand.base.id,
                    active = brand.base.active,
                    name = brand.name
            )
        }
    }

}