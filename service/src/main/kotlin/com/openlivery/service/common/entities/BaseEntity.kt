package com.openlivery.service.common.entities

import java.io.Serializable
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.persistence.*

@MappedSuperclass
abstract class BaseEntity : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
        private set

    @Column(name = "active")
    var active: Boolean = true

    @Column(name = "created_date_time")
    var createdDateTime: LocalDateTime? = null
        private set

    @Column(name = "changed_date_time")
    var changedDateTime: LocalDateTime? = null
        private set

    @Version
    @Column(name = "version")
    var version: Int = 1
        private set

    fun isNew(): Boolean = id == null

    @PreUpdate
    @PrePersist
    fun onUpsert() {
        if(isNew() && createdDateTime == null)
            createdDateTime = LocalDateTime.now(ZoneOffset.UTC)
        changedDateTime = LocalDateTime.now(ZoneOffset.UTC)
    }
}
