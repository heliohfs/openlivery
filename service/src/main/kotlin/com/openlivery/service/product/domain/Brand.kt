package com.openlivery.service.product.entity

import com.openlivery.service.common.entities.BaseEntity
import javax.persistence.*

@Entity(name = "Brand")
@Table(name = "brand")
data class Brand(
        @Column(name = "brand_name")
        var name: String? = null
) : BaseEntity() {

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "brand"
    )
    var products: MutableList<Product> = mutableListOf()

}
