package com.openlivery.service.product.domain.entity

import com.openlivery.service.product.domain.enums.DiscountApplication
import com.openlivery.service.product.domain.enums.DiscountType
import java.math.BigDecimal
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "discount")
open class Discount(
        applyTo: DiscountApplication,
        campaign: Campaign,
        discountType: DiscountType,
        discount: BigDecimal,
) : BaseDiscount(applyTo, campaign, discountType, discount)