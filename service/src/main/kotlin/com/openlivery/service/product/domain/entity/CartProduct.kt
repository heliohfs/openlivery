package com.openlivery.service.product.domain.entity

import com.openlivery.service.product.domain.enums.DiscountAccess
import com.openlivery.service.product.domain.enums.DiscountType
import java.io.Serializable
import java.math.BigDecimal

class CartProduct(
        val id: Long,

        var amount: Int
) : Serializable {

    @Transient
    var basePrice: BigDecimal = BigDecimal.ZERO

    @Transient
    var finalPrice: BigDecimal = BigDecimal.ZERO

    @Transient
    var discountApplied: Boolean = false

    @Transient
    var discountSource: DiscountAccess? = null

    @Transient
    var discountType: DiscountType? = null

    @Transient
    var discount: BigDecimal? = null

    @Transient
    var pictureStorageKey: String? = null

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