package com.openlivery.service.common.domain.entity

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "customer_discount_claim")
@IdClass(CustomerDiscountClaim.Key::class)
class CustomerDiscountClaim(
        @Id
        @Column(name = "customer_identity_number")
        val customerIdentityNumber: String,

        @Id
        @Column(name = "discount_id")
        val discountId: Long,

        @Column(name = "claim_count")
        var count: Int = 0
) {

    class Key() : Serializable {
        var customerIdentityNumber: String = ""
        var discountId: Long = -1

        constructor(customerIdentityNumber: String, discountId: Long) : this() {
            this.customerIdentityNumber = customerIdentityNumber
            this.discountId = discountId
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Key

            if (customerIdentityNumber != other.customerIdentityNumber) return false
            if (discountId != other.discountId) return false

            return true
        }

        override fun hashCode(): Int {
            var result = customerIdentityNumber.hashCode()
            result = 31 * result + discountId.hashCode()
            return result
        }

    }

}