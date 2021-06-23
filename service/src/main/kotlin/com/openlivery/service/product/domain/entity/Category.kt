package com.openlivery.service.product.domain.entity

import com.openlivery.service.common.domain.entity.BaseEntity
import javax.persistence.*

@Entity
@Table(name = "category")
class Category(
        @Column(name = "category_name")
        var categoryName: String
) : BaseEntity() {

    @ManyToMany
    @JoinTable(
            name = "product_category",
            joinColumns = [JoinColumn(name = "category_id")],
            inverseJoinColumns = [JoinColumn(name = "product_id")]
    )
    var products: MutableSet<Product> = mutableSetOf()

}