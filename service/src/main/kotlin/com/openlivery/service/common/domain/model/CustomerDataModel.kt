package com.openlivery.service.common.domain.model

import com.openlivery.service.common.domain.entity.CustomerData
import com.openlivery.service.common.exception.IllegalEntityException

class CustomerDataModel private constructor(
        val id: Long,
        val completeName: String,
        val phoneNumber: String,
        val identityNumber: String
) {

    companion object {
        fun from(customerData: CustomerData): CustomerDataModel {
            return CustomerDataModel(
                    id = customerData.id ?: throw IllegalEntityException(),
                    completeName = customerData.completeName,
                    phoneNumber = customerData.phoneNumber,
                    identityNumber = customerData.identityNumber
            )
        }
    }
}