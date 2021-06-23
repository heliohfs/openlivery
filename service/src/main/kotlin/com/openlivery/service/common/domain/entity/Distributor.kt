package com.openlivery.service.common.domain.entity

import java.time.LocalTime
import javax.persistence.*

@Entity
@Table(name = "distributor")
class Distributor(
        @Column(name = "business_start_hour")
        var businessStartHour: LocalTime,

        @Column(name = "business_end_hour")
        var businessEndHour: LocalTime,

        @Column(name = "company_name")
        var companyName: String,

        @Column(name = "trade_name")
        var tradeName: String,

        @Column(name = "company_document")
        var companyDocument: String
): BaseEntity() {

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "distributor_address",
            joinColumns = [JoinColumn(name = "distributor_id")],
            inverseJoinColumns = [JoinColumn(name = "address_id")]
    )
    var distributorAddress: MutableSet<Address> = mutableSetOf()


}