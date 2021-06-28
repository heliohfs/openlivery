package com.openlivery.service.product.domain.entity

import java.math.BigDecimal
import javax.persistence.*

@Entity(name = "Product")
@Table(name = "product")
class Product(name: String, basePrice: BigDecimal) : BaseProduct(name, basePrice)
