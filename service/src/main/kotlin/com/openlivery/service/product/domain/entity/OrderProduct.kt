package com.openlivery.service.product.domain.entity

import java.io.Serializable
import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "order_product")
@IdClass(OrderProduct.Key::class)
data class OrderProduct(
        @Id
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "product_id")
        val product: Product? = null,

        @Id
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "order_id")
        val order: Order? = null,

        @Column(name = "amount")
        val amount: Int,

        @Column(name = "price")
        val price: BigDecimal
) {

    class Key() : Serializable {
        var order: Long = -1
        var product: Long = -1

        constructor(order: Long, product: Long) : this() {
            this.order = order
            this.product = product
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Key

            if (order != other.order) return false
            if (product != other.product) return false

            return true
        }

        override fun hashCode(): Int {
            var result = order.hashCode()
            result = 31 * result + product.hashCode()
            return result
        }
    }

}