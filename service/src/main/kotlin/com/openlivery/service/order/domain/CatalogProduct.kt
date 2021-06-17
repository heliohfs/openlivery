package com.openlivery.service.order.domain

import org.hibernate.annotations.Immutable
import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity(name = "OrderCatalogProduct")
@Table(name = "catalog_product")
@Immutable
class CatalogProduct private constructor(
        @Id
        val id: Long,

        @Column(name = "final_price")
        val finalPrice: BigDecimal,

        @Column(name = "base_price")
        val basePrice: BigDecimal,

        @Column(name = "discount_applied")
        val discountApplied: Boolean,

        @Column(name = "picture_storage_key")
        val pictureStorageKey: String,

        @Column(name = "decimal_discount")
        val decimalDiscount: BigDecimal? = null

) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CatalogProduct

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}