package com.openlivery.service.customer.domain.dto

import com.openlivery.service.common.domain.Address
import java.math.BigDecimal

class AddressModel(
        var id: Long,
        var streetName: String? = null,
        var streetNumber: Int? = null,
        var cityName: String? = null,
        var governingDistrict: String? = null,
        var country: String? = null,
        var additionalInfo: String? = null,
        var latitude: BigDecimal? = null,
        var longitude: BigDecimal? = null
) {
    companion object {
        fun from(address: Address): AddressModel {
            return AddressModel(
                    id = address.id!!,
                    streetName = address.streetName,
                    streetNumber = address.streetNumber,
                    cityName = address.cityName,
                    governingDistrict = address.governingDistrict,
                    country = address.country,
                    additionalInfo = address.additionalInfo,
                    latitude = address.latitude,
                    longitude = address.longitude
            )
        }
    }
}