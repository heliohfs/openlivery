package com.openlivery.service.order.domain

import com.openlivery.service.order.domain.enums.DiscountType
import org.springframework.data.annotation.Immutable
import java.math.BigDecimal
import javax.persistence.*

@Entity(name = "OrderDiscount")
@Table(name = "campaign_discount")
@Immutable
class Discount private constructor(
        @Id
        val id: Long,

        @Column(name = "campaign_id")
        val campaignId: Long,

        @Enumerated(EnumType.STRING)
        @Column(name = "discount_type")
        val discountType: DiscountType,

        @Column(name = "decimal_discount")
        val decimalDiscount: BigDecimal,

        @Column(name = "max_order_discount")
        val maxOrderDiscount: BigDecimal,

        @Column(name = "min_order_value")
        val minOrderValue: BigDecimal,

        @ManyToOne
        @JoinColumn(name = "product_id")
        val product: CatalogProduct? = null
)