package com.openlivery.service.product.domain.entity

import com.openlivery.service.common.domain.entity.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "brand")
data class Brand(
        @Column(name = "brand_name")
        var name: String
) : BaseEntity() {

    @OneToMany(mappedBy = "brand")
    var products: MutableSet<Product> = mutableSetOf()

}
