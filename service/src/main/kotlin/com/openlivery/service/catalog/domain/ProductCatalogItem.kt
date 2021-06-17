package com.openlivery.service.catalog.domain

import org.springframework.data.annotation.Immutable
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*

@Entity(name = "CatalogProduct")
@Table(name = "catalog_product")
@Immutable
class ProductCatalogItem private constructor(
        @Id
        val id: Long,

        @Column(name = "active")
        val active: Boolean,

        @Column(name = "created_date_time")
        val createdDateTime: LocalDateTime,

        @Column(name = "changed_date_time")
        val changedDateTime: LocalDateTime,

        @Column(name = "version")
        val version: Long,

        @Column(name = "picture_storage_key")
        val pictureStorageKey: String? = null,

        @Column(name = "item_code")
        val itemCode: String? = null,

        @Column(name = "brand_id")
        val brandId: Long? = null,

        @Column(name = "product_name")
        val name: String? = null,

        @Column(name = "base_price")
        val basePrice: BigDecimal,

        @Column(name = "final_price")
        val finalPrice: BigDecimal,

        @Column(name = "description")
        val description: String? = null,

        @Column(name = "decimal_discount")
        val decimalDiscount: BigDecimal? = null,

        @Column(name = "discount_applied")
        val discountApplied: Boolean,

        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(
                name = "product_category",
                joinColumns = [JoinColumn(name = "product_id")],
                inverseJoinColumns = [JoinColumn(name = "category_id")]
        )
        val categories: List<ProductCatalogItemCategory> = listOf()
)