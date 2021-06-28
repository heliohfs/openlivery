package com.openlivery.service.product.domain.entity

import com.openlivery.service.product.domain.enums.DiscountAccess
import com.openlivery.service.product.domain.enums.DiscountApplication
import com.openlivery.service.product.domain.enums.DiscountType
import java.math.BigDecimal
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "campaign_discount")
@DiscriminatorValue(DiscountApplication.DeliveryFee.name)
class DeliveryFeeDiscount(
        campaign: Campaign,
        accessBy: DiscountAccess,
        discountType: DiscountType,
        discount: BigDecimal,
) : Discount(campaign, accessBy, discountType, discount)