package com.openlivery.service.product.service

import com.openlivery.service.product.domain.Brand
import com.openlivery.service.product.repository.BrandRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class BrandService(
        val repository: BrandRepository
) {

    fun findAll(): List<Brand> {
        return repository.findAll()
    }

    fun findById(id: Long): Optional<Brand> {
        return repository.findById(id)
    }

    fun save(brand: Brand): Brand {
        return repository.save(brand)
    }

    fun deleteById(id: Long) {
        repository.deleteById(id)
    }

}