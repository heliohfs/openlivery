package com.openlivery.service.product.domain

import com.openlivery.service.common.entities.BaseEntity
import java.math.BigDecimal
import javax.persistence.*

@Entity(name = "Product")
@Table(name = "product")
data class Product(
        @Column(name = "product_name")
        var name: String,

        @Column(name = "base_price")
        var price: BigDecimal
) : BaseEntity() {

    @Column(name = "description")
    var description: String? = null

    @Column(name = "item_code")
    var itemCode: String? = null

    @Column(name = "picture_storage_key")
    var pictureStorageKey: String? = null

    @ManyToOne
    @JoinColumn(name = "brand_id")
    var brand: Brand? = null

    @ManyToMany
    @JoinTable(
            name = "product_category",
            joinColumns = [JoinColumn(name = "product_id")],
            inverseJoinColumns = [JoinColumn(name = "category_id")]
    )
    var categories: MutableList<Category> = mutableListOf()

    @Transient
    var pictureUrl: String? = null

    @PostLoad
    @PostUpdate
    @PostPersist
    fun postLoad() {
        pictureUrl = pictureStorageKey
    }
}
