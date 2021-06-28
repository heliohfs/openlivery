package com.openlivery.service.product.domain.model

import com.openlivery.service.product.domain.entity.CartDeliveryAddress
import java.math.BigDecimal

class CartDeliveryAddressModel private constructor(
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
        fun from(address: CartDeliveryAddress): CartDeliveryAddressModel {
            return CartDeliveryAddressModel(
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