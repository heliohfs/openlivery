package com.openlivery.service.customer.domain

import com.openlivery.service.common.entities.BaseEntity
import java.math.BigDecimal
import javax.persistence.*

@Entity(name = "Customer")
@Table(name = "customer")
data class Customer(
        @Column(name = "complete_name")
        var completeName: String,

        @Column(name = "phone_number")
        var phoneNumber: String,
) : BaseEntity() {

    @Column(name = "oauth_id")
    var oauthId: String? = null

    @Column(name = "ref_code")
    var refCode: String? = null

    @Column(name = "email")
    var email: String? = null

    @Column(name = "default_address_id")
    var defaultAddressId: Long? = null

    @Column(name = "balance")
    val balance: BigDecimal? = BigDecimal.ZERO

    @OneToMany(mappedBy = "customer")
    val addresses: MutableList<CustomerAddress> = mutableListOf()

}