package com.openlivery.service.product.domain.entity

import com.openlivery.service.product.domain.enums.DiscountAccess
import com.openlivery.service.product.domain.enums.DiscountApplication
import com.openlivery.service.product.domain.enums.DiscountType
import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "campaign_discount")
@DiscriminatorValue(DiscountApplication.Product.name)
class ProductDiscount(
        campaign: Campaign,
        accessBy: DiscountAccess,
        discountType: DiscountType,
        discount: BigDecimal,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "product_id")
        val product: Product
) : Discount(campaign, accessBy, discountType, discount)