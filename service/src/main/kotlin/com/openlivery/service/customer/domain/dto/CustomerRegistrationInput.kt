package com.openlivery.service.customer.domain.dto

import com.openlivery.service.common.domain.Address
import com.openlivery.service.customer.domain.Customer
import java.math.BigDecimal

class CustomerRegistrationInput(
        private val completeName: String,
        private val phoneNumber: String,
        private val streetName: String,
        private val streetNumber: Int,
        private val cityName: String,
        private val governingDistrict: String,
        private val country: String,
        private val additionalInfo: String? = null,
        private val latitude: BigDecimal? = null,
        private val longitude: BigDecimal? = null
) {

    fun toCustomer(oauthId: String): Customer {
        return Customer(
                oauthId = oauthId,
                completeName = this.completeName,
                phoneNumber = this.phoneNumber
        ).also { customer ->
            customer.defaultAddress = Address(
                    streetName = this.streetName,
                    streetNumber = this.streetNumber,
                    cityName = this.cityName,
                    governingDistrict = this.governingDistrict,
                    country = this.country,
                    additionalInfo = this.additionalInfo,
                    latitude = this.latitude,
                    longitude = this.longitude
            )
        }
    }

}