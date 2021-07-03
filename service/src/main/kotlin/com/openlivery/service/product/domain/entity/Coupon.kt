package com.openlivery.service.product.domain.entity

import javax.persistence.*

@Entity
@Table(name = "coupon")
class Coupon(
        @Id
        @Column(name = "code")
        var code: String,

        @Column(name = "active")
        var active: Boolean = true
) {

        @OneToMany(fetch = FetchType.LAZY, mappedBy = "coupon")
        var discounts: MutableSet<Discount> = hashSetOf()

}