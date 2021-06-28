package com.openlivery.service.product.domain.entity

import com.openlivery.service.common.domain.entity.BaseEntity
import com.openlivery.service.product.domain.enums.DiscountAccess
import com.openlivery.service.product.domain.enums.DiscountType
import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "campaign_discount")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "apply_to", discriminatorType = DiscriminatorType.STRING)
abstract class Discount(
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "campaign_id")
        val campaign: Campaign,

        @Column(name = "access_by")
        @Enumerated(EnumType.STRING)
        var accessBy: DiscountAccess,

        @Column(name = "discount_type")
        @Enumerated(EnumType.STRING)
        var discountType: DiscountType,

        @Column(name = "discount")
        var discount: BigDecimal,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "coupon")
        var coupon: Coupon? = null
) : BaseEntity()