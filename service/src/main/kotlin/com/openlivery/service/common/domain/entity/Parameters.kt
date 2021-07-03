package com.openlivery.service.common.domain.entity

import java.math.BigDecimal
import java.time.LocalTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "system_parameters")
class Parameters {

    @Id
    val id: Short = 1

    @Column(name = "min_order_value")
    var minOrderValue: BigDecimal = BigDecimal.ZERO

    @Column(name = "min_delivery_fee_value")
    var minDeliveryFeeValue: BigDecimal = BigDecimal.ZERO

    @Column(name = "delivery_fee_value_per_unit")
    var deliveryFeeValuePerUnit: BigDecimal = BigDecimal.ZERO

    @Column(name = "min_delivery_fee_distance_threshold")
    var minDeliveryFeeDistanceThreshold: Int = 0

    @Column(name = "maximum_delivery_radius")
    var maximumDeliveryRadius: Int = 0

    @Column(name = "startup_order_creation_at")
    var startupOrderCreationAt: LocalTime? = null

    @Column(name = "shutdown_order_creation_at")
    var shutdownOrderCreationAt: LocalTime? = null

}