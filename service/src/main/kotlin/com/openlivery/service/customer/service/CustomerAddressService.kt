package com.openlivery.service.customer.service

import com.openlivery.service.customer.domain.CustomerAddress
import com.openlivery.service.customer.repository.CustomerAddressRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class CustomerAddressService(
        val repository: CustomerAddressRepository
) {

    fun save(customerAddress: CustomerAddress): CustomerAddress {
        return repository.save(customerAddress)
    }

    fun findAll(): List<CustomerAddress> {
        return repository.findAll()
    }

    fun findById(id: Long): Optional<CustomerAddress> {
        return repository.findById(id)
    }

    fun deleteById(id: Long) {
        repository.deleteById(id)
    }

}