package com.openlivery.service.product.domain.entity

import java.io.Serializable
import java.math.BigDecimal

class CartProduct(
        val id: Long,
        var amount: Int
) : Serializable {

    @Transient
    var basePrice: BigDecimal = BigDecimal.ZERO

    @Transient
    var discount: ProductDiscount? = null
        set(discount) {
            field = discount?.let {
                val discountPrice = it.applyTo(basePrice)
                when {
                    field == null -> it
                    discountPrice.compareTo(finalPrice) == -1 -> it
                    else -> field
                }
            }
        }

    @Transient
    var pictureStorageKey: String? = null

    val finalPrice: BigDecimal
        get() = discount?.applyTo(basePrice) ?: basePrice

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CartProduct

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}