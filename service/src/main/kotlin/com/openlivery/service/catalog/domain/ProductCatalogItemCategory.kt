package com.openlivery.service.catalog.domain

import com.openlivery.service.common.domain.BaseEntity
import org.hibernate.annotations.Immutable
import javax.persistence.*

@Entity(name = "CatalogProductCategory")
@Table(name = "category")
@Immutable
class ProductCatalogItemCategory(
        @Column(name = "category_name")
        val categoryName: String
) : BaseEntity()