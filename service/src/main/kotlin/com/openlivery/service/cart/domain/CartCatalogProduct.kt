package com.openlivery.service.cart.domain

import org.hibernate.annotations.Immutable
import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity(name = "CartCatalogProduct")
@Table(name = "product_catalog")
@Immutable
data class CartCatalogProduct(
        @Id
        val id: Long,

        @Column(name = "final_price")
        val finalPrice: BigDecimal,

        @Column(name = "base_price")
        val basePrice: BigDecimal,

        @Column(name = "picture_storage_key")
        var pictureStorageKey: String? = null
) {

    @Transient
    var amount: Int = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CartCatalogProduct

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}