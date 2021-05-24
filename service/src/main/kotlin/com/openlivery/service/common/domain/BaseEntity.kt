package com.openlivery.service.common.domain

import java.io.Serializable
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.persistence.*

@MappedSuperclass
abstract class BaseEntity : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = null

    @Column(name = "active")
    open var active: Boolean? = true

    @Column(name = "created_date_time", insertable = false, updatable = false)
    open var createdDateTime: LocalDateTime? = null

    @Column(name = "changed_date_time")
    open var changedDateTime: LocalDateTime? = null

    @Version
    @Column(name = "version")
    open var version: Int? = 1

    fun isNew(): Boolean = id == null

    @PrePersist
    @PreUpdate
    fun onPersist() {
        if (isNew())
            createdDateTime = LocalDateTime.now(ZoneOffset.UTC)
        changedDateTime = LocalDateTime.now(ZoneOffset.UTC)
    }
}
