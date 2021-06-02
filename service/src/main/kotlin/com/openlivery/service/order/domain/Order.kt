package com.openlivery.service.order.domain

import com.openlivery.service.common.domain.Address
import com.openlivery.service.common.domain.BaseEntity
import java.math.BigDecimal
import javax.persistence.*

@Entity(name = "Order")
@Table(name = "\"order\"")
data class Order(
        @ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
        @JoinColumn(name = "delivery_address_id")
        val deliveryAddress: Address,

        @Column(name = "notes")
        val notes: String? = null,

        @ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
        @JoinColumn(name = "customer_data_id")
        val customerData: OrderCustomerData
) : BaseEntity() {

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "orderId")
    var orderProducts: MutableSet<OrderProduct> = mutableSetOf()

    @Column(name = "order_value")
    var orderValue: BigDecimal = BigDecimal.ZERO

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
    var status: OrderStatus? = OrderStatus.PLACED

    @Column(name = "non_completion_reason")
    @Enumerated(EnumType.STRING)
    var nonCompletionReason: OrderNonCompletionReason? = null

}