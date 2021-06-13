package com.openlivery.service.cart.repository

import com.openlivery.service.cart.domain.CartCatalogProduct
import com.openlivery.service.common.repository.ReadOnlyRepository
import org.springframework.stereotype.Repository

@Repository
interface CartCatalogProductRepository: ReadOnlyRepository<CartCatalogProduct, Long>