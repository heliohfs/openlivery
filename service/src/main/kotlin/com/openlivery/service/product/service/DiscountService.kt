package com.openlivery.service.product.service

import com.openlivery.service.common.domain.entity.Customer
import com.openlivery.service.product.domain.entity.*
import com.openlivery.service.product.repository.DiscountRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.util.*

@Service
@Transactional
class DiscountService(
        private val discountRepository: DiscountRepository
) {

}