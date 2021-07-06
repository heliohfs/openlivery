package com.openlivery.service.product.domain.entity

import com.openlivery.service.common.domain.entity.BaseEntity
import org.hibernate.annotations.Where
import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "product")
class Product(
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
    var categories: MutableSet<Category> = hashSetOf()

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    var discounts: MutableSet<ProductDiscount> = hashSetOf()

}