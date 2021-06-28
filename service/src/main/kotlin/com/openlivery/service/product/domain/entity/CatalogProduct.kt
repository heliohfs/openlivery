package com.openlivery.service.product.domain.entity

import com.openlivery.service.product.domain.enums.DiscountType
import org.springframework.data.annotation.Immutable
import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "catalog_product")
@Immutable
class CatalogProduct private constructor(
        name: String,

        basePrice: BigDecimal,

        @Column(name = "final_price")
        val finalPrice: BigDecimal,

        @Column(name = "discount_type")
        @Enumerated(EnumType.STRING)
        val discountType: DiscountType,

        @Column(name = "discount")
        val discount: BigDecimal,

        @Column(name = "discount_applied")
        val discountApplied: Boolean,
): BaseProduct(name, basePrice)