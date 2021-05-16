package com.openlivery.service.customer.service

import com.openlivery.service.customer.domain.Customer
import com.openlivery.service.customer.repository.CustomerRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class CustomerService(
    val repository: CustomerRepository
) {

    fun save(customer: Customer): Customer {
        return repository.save(customer)
    }

    fun findById(id: Long): Optional<Customer> {
        return repository.findById(id)
    }

    fun findByOauthId(oauthId: String): Optional<Customer> {
        return repository.findByOauthIdAndActiveIsTrue(oauthId)
    }

}