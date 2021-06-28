package com.openlivery.service.product.domain.entity

import java.io.Serializable
import java.math.BigDecimal

class CartDeliveryAddress (
        var streetName: String,

        var streetNumber: Int,

        var cityName: String,

        var governingDistrict: String,

        var country: String,

        var additionalInfo: String?,

        var latitude: BigDecimal?,

        var longitude: BigDecimal?
): Serializable