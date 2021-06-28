package com.openlivery.service.product.domain.entity

import com.openlivery.service.product.domain.enums.DiscountAccess
import com.openlivery.service.product.domain.enums.DiscountApplication
import com.openlivery.service.product.domain.enums.DiscountType
import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "campaign_discount")
@DiscriminatorValue(DiscountApplication.Order.name)
class OrderDiscount(
        campaign: Campaign,
        accessBy: DiscountAccess,
        discountType: DiscountType,
        discount: BigDecimal,
) : Discount(campaign, accessBy, discountType, discount)