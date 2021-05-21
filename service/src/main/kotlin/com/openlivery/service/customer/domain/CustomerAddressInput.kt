package com.openlivery.service.customer.domain

import java.math.BigDecimal

class CustomerAddressInput(
        var id: Long? = null,
        var latitude: BigDecimal,
        var longitude: BigDecimal,
        var streetName: String,
        var streetNumber: Int,
        var cityName: String,
        var governingDistrict: String,
        var country: String,
        var additionalInfo: String? = null
) {

    fun toCustomerAddress(): CustomerAddress {
        return CustomerAddress(
                latitude = latitude,
                longitude = longitude,
                streetName = streetName,
                streetNumber = streetNumber,
                cityName = cityName,
                governingDistrict = governingDistrict,
                country = country
        ).also {
            it.additionalInfo = additionalInfo
        }
    }

    fun updateCustomerAddress(customerAddress: CustomerAddress): CustomerAddress {
        return customerAddress.also {
            it.latitude = latitude
            it.longitude = longitude
            it.streetName = streetName
            it.streetNumber = streetNumber
            it.cityName = cityName
            it.governingDistrict = governingDistrict
            it.country = country
            it.additionalInfo = additionalInfo ?: it.additionalInfo
        }
    }


}