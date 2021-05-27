package com.openlivery.service.customer.domain

import javax.persistence.*

@Entity(name = "CustomerData")
@Table(name = "customer_data")
data class CustomerData(
        @Column(name = "complete_name")
        var completeName: String,

        @Column(name = "phone_number")
        var phoneNumber: String
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
        private set

    @OneToOne(mappedBy = "data")
    val customer: Customer? = null

}