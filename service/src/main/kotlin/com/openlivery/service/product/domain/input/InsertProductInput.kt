package com.openlivery.service.product.domain.input

import java.math.BigDecimal

class InsertProductInput private constructor(
        val name: String,
        val basePrice: BigDecimal,
        val categoriesIds: List<Long>?,
        val brandId: Long?,
        val description: String?,
        val itemCode: String?
)