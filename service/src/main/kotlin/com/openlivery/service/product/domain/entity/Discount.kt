package com.openlivery.service.product.domain.entity

import com.openlivery.service.common.domain.entity.BaseEntity
import com.openlivery.service.common.domain.entity.CustomerDiscountClaim
import com.openlivery.service.product.domain.enums.DiscountType
import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "discount")
@Inheritance(strategy = InheritanceType.JOINED)
abstract class Discount(
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "campaign_id")
        val campaign: Campaign,

        @Column(name = "kind")
        @Enumerated(EnumType.STRING)
        var kind: DiscountType,

        @Column(name = "discount")
        var discount: BigDecimal,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "claim_rule_id")
        val claimRule: ClaimRule? = null,

        @ManyToOne
        @JoinColumn(name = "coupon_code")
        var coupon: Coupon? = null
) : BaseEntity() {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "discountId")
    var claims: Set<CustomerDiscountClaim> = hashSetOf()

    fun applyTo(value: BigDecimal): BigDecimal {
        val subtrahend = if (kind === DiscountType.AMOUNT_OFF) discount else {
            if (this is OrderDiscount && maxDiscountValue != null) {
                value.multiply(discount).min(maxDiscountValue)
            } else if (this is DeliveryFeeDiscount && maxDiscountValue != null) {
                value.multiply(discount).min(maxDiscountValue)
            } else value.multiply(discount)
        }

        val result = value.subtract(subtrahend)

        return if (result.signum() == -1) {
            BigDecimal.ZERO
        } else result
    }

}