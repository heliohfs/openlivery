package com.openlivery.service.product.domain.entity

import com.openlivery.service.product.domain.enums.DiscountApplication
import com.openlivery.service.product.domain.enums.DiscountType
import org.springframework.data.annotation.Immutable
import java.math.BigDecimal
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "underlying_discount")
@Immutable
class UnderlyingDiscount(
        applyTo: DiscountApplication,
        campaign: Campaign,
        discountType: DiscountType,
        discount: BigDecimal,
) : BaseDiscount(applyTo, campaign, discountType, discount)