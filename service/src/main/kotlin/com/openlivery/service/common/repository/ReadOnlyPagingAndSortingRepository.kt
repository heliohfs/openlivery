package com.openlivery.service.common.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.Repository
import java.util.*

@NoRepositoryBean
interface ReadOnlyPagingAndSortingRepository<T, ID> : Repository<T, ID> {
    fun findAll(): List<T>

    fun findAll(sort: Sort?): Iterable<T>

    fun findAll(pageable: Pageable?): Page<T>

    fun findById(id: ID): Optional<T>
}