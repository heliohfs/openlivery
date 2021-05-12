package com.openlivery.service.product.domain

import com.openlivery.service.common.entities.BaseEntity
import javax.persistence.Column
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany

class Category(
        @Column(name = "category_name")
        val categoryName: String
) : BaseEntity() {

    @ManyToMany
    @JoinTable(
            name = "product_category",
            joinColumns = [JoinColumn(name = "category_id")],
            inverseJoinColumns = [JoinColumn(name = "product_id")]
    )
    var products: MutableList<Product> = mutableListOf()

}