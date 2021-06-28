package com.openlivery.service.common.domain.entity

import javax.persistence.*

@Entity
@Table(name = "authority")
data class Authority(
        @Column(name = "authority_name")
        val name: String
) {

    companion object {
        const val CREATE_PRODUCT = "CREATE_PRODUCT"
        const val UPDATE_PRODUCT = "UPDATE_PRODUCT"
        const val READ_PRODUCTS = "READ_PRODUCTS"
        const val DELETE_PRODUCT = "DELETE_PRODUCT"

        const val CREATE_BRAND = "CREATE_BRAND"
        const val UPDATE_BRAND = "UPDATE_BRAND"
        const val READ_BRANDS = "READ_BRANDS"
        const val DELETE_BRAND = "DELETE_BRAND"

        const val CREATE_CATEGORY = "CREATE_CATEGORY"
        const val UPDATE_CATEGORY = "UPDATE_CATEGORY"
        const val READ_CATEGORIES = "READ_CATEGORIES"
        const val DELETE_CATEGORY = "DELETE_CATEGORY"

        const val CREATE_ORDER = "CREATE_ORDER"

        const val READ_CATALOG = "READ_CATALOG"
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

}