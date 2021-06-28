package com.openlivery.service.common.domain.entity

import com.openlivery.service.product.domain.entity.Discount
import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "customer_applied_discount")
@IdClass(AppliedDiscount.Key::class)
class AppliedDiscount {

    @Id
    @ManyToOne
    @JoinColumn(name = "customer_identity_number")
    var customerData: CustomerData? = null

    @Id
    @ManyToOne
    @JoinColumn(name = "campaign_discount_id")
    var discount: Discount? = null

    @Column(name = "application_count")
    var applicationCount: Long = 1

    class Key() : Serializable {
        var customerData: String = ""
        var discount: Long = -1

        constructor(customerData: String, discount: Long) : this() {
            this.customerData = customerData
            this.discount = discount
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Key

            if (customerData != other.customerData) return false
            if (discount != other.discount) return false

            return true
        }

        override fun hashCode(): Int {
            var result = customerData.hashCode()
            result = 31 * result + discount.hashCode()
            return result
        }
    }
}