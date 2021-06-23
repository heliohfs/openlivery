package com.openlivery.service.product.domain.entity

import com.openlivery.service.product.domain.enums.DiscountType
import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "campaign_discount")
class CampaignDiscount(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        @ManyToOne
        @JoinColumn(name = "campaign_id")
        val campaign: Campaign,

        @Enumerated(EnumType.STRING)
        @Column(name = "discount_type")
        val discountType: DiscountType,

        @Column(name = "decimal_discount")
        var decimalDiscount: BigDecimal
) {

    @ManyToOne
    @JoinColumn(name = "product_id")
    var product: Product? = null

    @Column(name = "max_order_discount")
    var maxOrderDiscount: BigDecimal? = null

    @Column(name = "min_order_value")
    var minOrderValue: BigDecimal? = null

}