package com.openlivery.service.product.domain.entity

import com.openlivery.service.common.domain.entity.BaseEntity
import java.math.BigDecimal
import javax.persistence.*

@MappedSuperclass
abstract class BaseProduct(
        @Column(name = "product_name")
        var name: String,

        @Column(name = "base_price")
        var basePrice: BigDecimal
) : BaseEntity() {

    @Column(name = "description")
    var description: String? = null

    @Column(name = "item_code")
    var itemCode: String? = null

    @Column(name = "picture_storage_key")
    var pictureStorageKey: String? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    var brand: Brand? = null

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "product_category",
            joinColumns = [JoinColumn(name = "product_id")],
            inverseJoinColumns = [JoinColumn(name = "category_id")]
    )
    var categories: MutableSet<Category> = mutableSetOf()

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "productId")
    var availableDiscounts: MutableSet<UnderlyingDiscount> = hashSetOf()

}