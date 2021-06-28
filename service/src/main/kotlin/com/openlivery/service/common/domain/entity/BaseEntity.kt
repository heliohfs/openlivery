package com.openlivery.service.common.domain.entity

import com.openlivery.service.common.exception.IllegalEntityException
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

    abstract class Base {
        abstract val id: Long
        abstract val active: Boolean
        abstract val createdDateTime: LocalDateTime
        abstract val changedDateTime: LocalDateTime
        abstract val version: Int
    }

    @Transient
    val base = object : Base() {
        override val id: Long
            get() = this@BaseEntity.id ?: throw IllegalEntityException()

        override val active: Boolean
            get() = this@BaseEntity.active ?: throw IllegalEntityException()

        override val createdDateTime: LocalDateTime
            get() = this@BaseEntity.createdDateTime ?: throw IllegalEntityException()

        override val changedDateTime: LocalDateTime
            get() = this@BaseEntity.changedDateTime ?: throw IllegalEntityException()

        override val version: Int
            get() = this@BaseEntity.version ?: throw IllegalEntityException()
    }

    fun isNew(): Boolean = id == null

    @PrePersist
    @PreUpdate
    fun onPersist() {
        if (isNew())
            createdDateTime = LocalDateTime.now(ZoneOffset.UTC)
        changedDateTime = LocalDateTime.now(ZoneOffset.UTC)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BaseEntity

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }


}
