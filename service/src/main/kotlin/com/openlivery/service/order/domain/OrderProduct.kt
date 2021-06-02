package com.openlivery.service.order.domain

import java.io.Serializable
import javax.persistence.*

@Entity(name = "OrderProduct")
@Table(name = "order_product")
@IdClass(OrderProduct.OrderProductId::class)
data class OrderProduct(
        @Id
        @Column(name = "order_id")
        val orderId: Long,

        @Id
        @Column(name = "product_id")
        val productId: Long,

        @Column(name = "amount")
        val amount: Int
) {

    internal class OrderProductId() : Serializable {
        var orderId: Long = -1
        var productId: Long = -1

        constructor(orderId: Long, productId: Long) : this() {
            this.orderId = orderId
            this.productId = productId
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as OrderProductId

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