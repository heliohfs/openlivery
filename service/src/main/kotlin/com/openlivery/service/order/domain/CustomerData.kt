package com.openlivery.service.order.domain

import javax.persistence.*

@Entity(name = "OrderCustomerData")
@Table(name = "customer_data")
data class CustomerData(
        @Column(name = "complete_name")
        val completeName: String,

        @Column(name = "phone_number")
        val phoneNumber: String
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
        private set

}
