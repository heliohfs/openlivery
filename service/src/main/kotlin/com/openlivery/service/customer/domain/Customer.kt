package com.openlivery.service.customer.domain

import com.openlivery.service.common.domain.Address
import com.openlivery.service.common.domain.User
import java.math.BigDecimal
import javax.persistence.*

@Entity(name = "Customer")
@Table(name = "customer")
@PrimaryKeyJoinColumn(name = "user_id")
data class Customer(
        override val oauthId: String,
        override var completeName: String,
        override var phoneNumber: String
) : User(
        oauthId,
        completeName,
        phoneNumber
) {

    @Column(name = "balance")
    var balance: BigDecimal = BigDecimal.ZERO

    @ManyToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "default_address_id")
    var defaultAddress: Address? = null

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "customer_address",
            joinColumns = [JoinColumn(name = "customer_id")],
            inverseJoinColumns = [JoinColumn(name = "address_id")]
    )
    var addresses: MutableList<Address> = mutableListOf()

}