package com.openlivery.service.common.repository

import org.springframework.data.domain.Sort
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.Repository
import java.util.*

@NoRepositoryBean
interface ReadOnlyRepository<T, ID> : Repository<T, ID> {
    fun findAll(): List<T>

    fun findAll(sort: Sort?): Iterable<T>

    fun findById(id: ID): Optional<T>
}