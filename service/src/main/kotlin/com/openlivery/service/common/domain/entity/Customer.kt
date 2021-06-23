package com.openlivery.service.common.domain.entity

import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "customer")
@PrimaryKeyJoinColumn(name = "user_id")
data class Customer(
        override val oauthId: String,

        @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
        @JoinColumn(name = "customer_data_id", referencedColumnName = "id")
        val data: CustomerData
) : User(oauthId) {

    @Column(name = "balance")
    var balance: BigDecimal = BigDecimal.ZERO

    @ManyToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "default_address_id")
    var defaultAddress: Address? = null

    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.MERGE])
    @JoinTable(
            name = "customer_address",
            joinColumns = [JoinColumn(name = "customer_id")],
            inverseJoinColumns = [JoinColumn(name = "address_id")]
    )
    var addresses: MutableSet<Address> = mutableSetOf()

}