package com.openlivery.service.customer.domain.dto

import com.openlivery.service.customer.domain.Customer
import java.math.BigDecimal

class CustomerModel(
        val id: Long,
        val completeName: String,
        val phoneNumber: String,
        val defaultAddress: AddressModel,
        val balance: BigDecimal,
        val addresses: List<AddressModel>
) {
    companion object {
        fun from(customer: Customer): CustomerModel {
            return CustomerModel(
                    id = customer.id!!,
                    completeName = customer.data.completeName,
                    phoneNumber = customer.data.phoneNumber,
                    defaultAddress = AddressModel.from(customer.defaultAddress!!),
                    balance = customer.balance,
                    addresses = customer.addresses.map { AddressModel.from(it) }
            )
        }
    }
}