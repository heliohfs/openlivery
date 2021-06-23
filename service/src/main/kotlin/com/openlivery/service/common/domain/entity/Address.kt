package com.openlivery.service.common.domain.entity

import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "address")
data class Address(
        @Column(name = "street_name")
        var streetName: String? = null,

        @Column(name = "street_number")
        var streetNumber: Int? = null,

        @Column(name = "city_name")
        var cityName: String? = null,

        @Column(name = "governing_district")
        var governingDistrict: String? = null,

        @Column(name = "country")
        var country: String? = null,

        @Column(name = "additional_info")
        var additionalInfo: String? = null,

        @Column(name = "latitude")
        var latitude: BigDecimal? = null,

        @Column(name = "longitude")
        var longitude: BigDecimal? = null
) : BaseEntity()