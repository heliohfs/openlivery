package com.openlivery.service.product.repository

import com.openlivery.service.common.repository.ReadOnlyRepository
import com.openlivery.service.product.domain.entity.UnderlyingDiscount
import org.springframework.stereotype.Repository

@Repository
interface UnderlyingDiscountRepository : ReadOnlyRepository<UnderlyingDiscount, Long>{
}