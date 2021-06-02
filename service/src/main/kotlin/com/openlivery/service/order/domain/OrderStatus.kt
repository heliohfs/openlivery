package com.openlivery.service.order.domain

enum class OrderStatus {
    CANCELED,
    INCOMPLETE,
    PLACED,
    TIMEOUT,
    ACCEPTED,
    ON_ROUTE,
    FINISHED
}