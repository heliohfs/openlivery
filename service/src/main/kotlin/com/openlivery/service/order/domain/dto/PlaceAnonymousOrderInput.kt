package com.openlivery.service.order.domain.dto

import com.openlivery.service.common.domain.Address
import com.openlivery.service.order.domain.Order
import com.openlivery.service.order.domain.CustomerData
import java.math.BigDecimal

class PlaceAnonymousOrderInput(
        val completeName: String,
        val phoneNumber: String,
        val streetName: String,
        val streetNumber: Int,
        val identityNumber: String,
        val cityName: String,
        val governingDistrict: String,
        val country: String,
        val additionalInfo: String,
        val notes: String? = null
)
