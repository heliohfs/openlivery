package com.openlivery.service.product.domain

import com.openlivery.service.common.entities.BaseEntity
import javax.persistence.*

@Entity(name = "Category")
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
    var products: MutableList<Product>? = mutableListOf()

}