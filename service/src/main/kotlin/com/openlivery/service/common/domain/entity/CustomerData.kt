package com.openlivery.service.common.domain.entity

import javax.persistence.*

@Entity
@Table(name = "customer_data")
data class CustomerData(
        @Column(name = "complete_name")
        var completeName: String,

        @Column(name = "phone_number")
        var phoneNumber: String
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @OneToOne(mappedBy = "data")
    val customer: Customer? = null

}
