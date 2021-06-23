package com.openlivery.service.common.domain.entity

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "customer_coupon")
@IdClass(CustomerCoupon.Key::class)
class CustomerCoupon(
        @Id
        @Column(name = "customer_identity_number")
        val customerIdentityNumber: String,

        @Id
        @Column(name = "coupon_code")
        val couponCode: String,

        @Column(name = "application_count")
        var applicationCount: Long = 0
) {

    class Key() : Serializable {
        var customerIdentityNumber: String = ""
        var couponCode: String = ""

        constructor(customerIdentityNumber: String, couponCode: String) : this() {
            this.customerIdentityNumber = customerIdentityNumber
            this.couponCode = couponCode
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Key

            if (customerIdentityNumber != other.customerIdentityNumber) return false
            if (couponCode != other.couponCode) return false

            return true
        }

        override fun hashCode(): Int {
            var result = customerIdentityNumber.hashCode()
            result = 31 * result + couponCode.hashCode()
            return result
        }

    }

}