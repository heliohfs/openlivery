package com.openlivery.service.product.domain

import com.openlivery.service.common.domain.BaseEntity
import javax.persistence.*

@Entity(name = "Brand")
@Table(name = "brand")
data class Brand(
        @Column(name = "brand_name")
        var name: String
) : BaseEntity() {

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "brand"
    )
    var products: MutableList<Product>? = mutableListOf()

}
