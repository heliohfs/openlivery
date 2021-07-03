package com.openlivery.service.common.domain.entity

import com.openlivery.service.product.domain.entity.Order
import javax.persistence.*

@Entity
@Table(name = "customer_data")
data class CustomerData(
        @Column(name = "complete_name")
        var completeName: String,

        @Column(name = "phone_number")
        var phoneNumber: String,

        @Column(name = "identity_number")
        var identityNumber: String
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @OneToOne(mappedBy = "data")
    val customer: Customer? = null

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customerData")
    val ordersPlaced: Set<Order> = hashSetOf()

}
