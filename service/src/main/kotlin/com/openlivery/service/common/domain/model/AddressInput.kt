package com.openlivery.service.common.domain.model

import java.math.BigDecimal

class AddressInput private constructor(
        val streetName: String,
        val streetNumber: Int,
        val cityName: String,
        val governingDistrict: String,
        val country: String,
        val additionalInfo: String,
        val latitude: BigDecimal?,
        val longitude: BigDecimal?
)
