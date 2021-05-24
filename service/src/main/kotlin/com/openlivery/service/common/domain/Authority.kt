package com.openlivery.service.common.domain

import javax.persistence.*

@Entity(name = "Authority")
@Table(name = "authority")
data class Authority(
        @Column(name = "authority_name")
        val name: String
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

}