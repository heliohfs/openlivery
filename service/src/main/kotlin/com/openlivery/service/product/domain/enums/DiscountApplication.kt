package com.openlivery.service.product.domain.enums

sealed class DiscountApplication {

    object Order : DiscountApplication() {
        const val name = "ORDER"
    }

    object DeliveryFee : DiscountApplication() {
        const val name = "DELIVERY_FEE"
    }

    object Product : DiscountApplication() {
        const val name = "PRODUCT"
    }
}