package com.openlivery.service.product.domain.entity

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "order_product")
@IdClass(OrderProduct.Key::class)
data class OrderProduct(
        @Id
        @Column(name = "product_id")
        val productId: Long,

        @Column(name = "amount")
        val amount: Int
) {

    @Id
    @Column(name = "order_id")
    var orderId: Long? = null

    class Key() : Serializable {
        var orderId: Long = -1
        var productId: Long = -1

        constructor(orderId: Long, productId: Long) : this() {
            this.orderId = orderId
            this.productId = productId
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Key

            if (orderId != other.orderId) return false
            if (productId != other.productId) return false

            return true
        }

        override fun hashCode(): Int {
            var result = orderId.hashCode()
            result = 31 * result + productId.hashCode()
            return result
        }
    }

}