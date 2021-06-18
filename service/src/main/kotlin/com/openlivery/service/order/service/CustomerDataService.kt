package com.openlivery.service.order.service

import com.openlivery.service.order.domain.CustomerData
import com.openlivery.service.order.repository.CustomerDataRepository
import org.springframework.stereotype.Service
import java.util.*

@Service("OrderCustomerDataService")
class CustomerDataService(
        private val repository: CustomerDataRepository
) {

    fun findByIdentityNumber(identityNumber: String): Optional<CustomerData> {
        return repository.findByIdentityNumber(identityNumber)
    }

}