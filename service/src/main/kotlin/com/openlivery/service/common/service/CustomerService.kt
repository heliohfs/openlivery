package com.openlivery.service.common.service

import com.openlivery.service.common.domain.entity.Customer
import com.openlivery.service.common.repository.AddressRepository
import com.openlivery.service.common.repository.CustomerRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class CustomerService(
    private val customerRepository: CustomerRepository,
    private val addressRepository: AddressRepository
) {

    fun save(customer: Customer): Customer {
        return customerRepository.save(customer)
    }

    fun findById(id: Long): Optional<Customer> {
        return customerRepository.findById(id)
    }

    fun findByOauthId(oauthId: String): Optional<Customer> {
        return customerRepository.findByOauthIdAndActiveIsTrue(oauthId)
    }

}