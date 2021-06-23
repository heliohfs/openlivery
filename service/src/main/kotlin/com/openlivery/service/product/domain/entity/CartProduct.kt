package com.openlivery.service.product.domain.entity

import java.io.Serializable

class CartProduct(
        val id: Long,
        var amount: Int,
): Serializable {

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