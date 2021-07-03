package com.openlivery.service.product.domain.entity

import com.openlivery.service.common.domain.entity.Address
import com.openlivery.service.common.domain.entity.BaseEntity
import com.openlivery.service.common.domain.entity.CustomerData
import com.openlivery.service.product.domain.enums.OrderNonCompletionReason
import com.openlivery.service.product.domain.enums.OrderStatus
import java.math.BigDecimal
import javax.persistence.*
import kotlin.jvm.Transient

@Entity
@Table(name = "\"order\"")
data class Order(
        @ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
        @JoinColumn(name = "delivery_address_id")
        val deliveryAddress: Address,

        @Column(name = "notes")
        val notes: String? = null,

        @ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
        @JoinColumn(name = "customer_data_id")
        val customerData: CustomerData,

        @Column(name = "order_code")
        var orderCode: String?
) : BaseEntity() {

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = [CascadeType.ALL],
            mappedBy = "order"
    )
    var orderProducts: MutableSet<OrderProduct> = mutableSetOf()

    @Column(name = "delivery_fee")
    var deliveryFee: BigDecimal = BigDecimal.ZERO

    @Column(name = "deliveryman_id")
    var deliverymanId: Long? = null

    @Column(name = "distributor_id")
    var distributorId: Long? = null

    @Column(name = "customer_rating")
    var customerRating: Int? = null

    @Column(name = "deliveryman_rating")
    var deliverymanRating: Int? = null

    @Column(name = "customer_rating_reason")
    var customerRatingReason: String? = null

    @Column(name = "deliveryman_rating_reason")
    var deliverymanRatingReason: String? = null

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    var status: OrderStatus = OrderStatus.PLACED

    @Column(name = "non_completion_reason")
    @Enumerated(EnumType.STRING)
    var nonCompletionReason: OrderNonCompletionReason? = null

    @Column(name = "coupon_applied")
    var couponApplied: String? = null

    @Transient
    var total: BigDecimal = BigDecimal.ZERO
        private set
        get() = orderProducts.fold(BigDecimal.ZERO, { total, it -> total.add(it.price.multiply(BigDecimal(it.amount))) }).add(deliveryFee)

    @Transient
    var codeAvailable: Boolean = true
        private set
        get() = listOf(
                OrderStatus.CANCELED,
                OrderStatus.FINISHED,
                OrderStatus.TIMEOUT,
                OrderStatus.INCOMPLETE
        ).contains(status)

}