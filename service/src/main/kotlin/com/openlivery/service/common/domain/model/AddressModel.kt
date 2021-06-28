package com.openlivery.service.common.domain.model

import com.openlivery.service.common.domain.entity.Address
import java.math.BigDecimal

class AddressModel private constructor(
        val id: Long,
        val streetName: String,
        val streetNumber: Int,
        val cityName: String,
        val governingDistrict: String,
        val country: String,
        val additionalInfo: String?,
        val latitude: BigDecimal?,
        val longitude: BigDecimal?
) {

    companion object {
        fun from(address: Address): AddressModel {
            return AddressModel(
                    id = address.base.id,
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