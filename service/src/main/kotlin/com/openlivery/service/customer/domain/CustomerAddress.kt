package com.openlivery.service.customer.domain

import com.openlivery.service.common.entities.BaseEntity
import java.math.BigDecimal
import javax.persistence.*

@Entity(name = "CustomerAddress")
@Table(name = "customer_address")
class CustomerAddress(
        @Column(name = "latitude")
        var latitude: BigDecimal,
        @Column(name = "longitude")
        var longitude: BigDecimal,
        @Column(name = "street_name")
        var streetName: String,
        @Column(name = "street_number")
        var streetNumber: Int,
        @Column(name = "city_name")
        var cityName: String,
        @Column(name = "governing_district")
        var governingDistrict: String,
        @Column(name = "country")
        var country: String,
) : BaseEntity() {

    @Column(name = "additional_info")
    var additionalInfo: String? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    lateinit var customer: Customer

}