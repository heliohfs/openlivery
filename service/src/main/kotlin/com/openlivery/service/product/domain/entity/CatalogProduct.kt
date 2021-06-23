package com.openlivery.service.product.domain.entity

import org.springframework.data.annotation.Immutable
import java.math.BigDecimal
import javax.persistence.*

@Entity(name = "CatalogProduct")
@Immutable
@Table(name = "catalog_product")
class CatalogProduct private constructor(
        @Id
        val id: Long,

        @Column(name = "product_name")
        val name: String,

        @Column(name = "base_price")
        val basePrice: BigDecimal,

        @Column(name = "description")
        val description: String,

        @Column(name = "item_code")
        val itemCode: String? = null,

        @Column(name = "picture_storage_key")
        val pictureStorageKey: String? = null,

        @ManyToOne
        @JoinColumn(name = "brand_id")
        val brand: Brand? = null,

        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(
                name = "product_category",
                joinColumns = [JoinColumn(name = "product_id")],
                inverseJoinColumns = [JoinColumn(name = "category_id")]
        )
        val categories: MutableSet<Category> = mutableSetOf(),

        @Column(name = "final_price")
        val finalPrice: BigDecimal,

        @Column(name = "decimal_discount")
        val decimalDiscount: BigDecimal? = null,

        @Column(name = "discount_applied")
        val discountApplied: Boolean
)