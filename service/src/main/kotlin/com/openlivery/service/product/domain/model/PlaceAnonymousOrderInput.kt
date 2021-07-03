package com.openlivery.service.product.domain.model

class PlaceAnonymousOrderInput(
        val completeName: String,
        val phoneNumber: String,
        val identityNumber: String,
        val notes: String?
)