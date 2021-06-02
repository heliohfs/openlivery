package com.openlivery.service.order.domain

import org.hibernate.annotations.Immutable
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity(name = "OrderCatalogProduct")
@Table(name = "product_catalog")
@Immutable
data class OrderCatalogProduct(
        @Id
        val id: Long,

        @Column(name = "final_price")
        val finalPrice: BigDecimal
)