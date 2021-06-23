package com.openlivery.service.common.service

import com.openlivery.service.common.domain.entity.Customer
import com.openlivery.service.common.repository.CustomerRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class CustomerService(
    private val repository: CustomerRepository
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