package com.openlivery.service.common.domain.entity

import javax.persistence.*

@Entity
@Table(name = "authority")
data class Authority(
        @Column(name = "authority_name")
        val name: String
) {

    companion object {
        const val PLACE_ORDER = "place_order"
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

}