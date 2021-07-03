package com.openlivery.service.product.domain.model

import com.openlivery.service.common.exception.IllegalEntityException
import com.openlivery.service.product.domain.entity.OrderProduct
import java.math.BigDecimal

class OrderProductModel private constructor(
        val product: ProductModel,
        val amount: Int,
        val price: BigDecimal
) {

    companion object {
        fun from(orderProduct: OrderProduct): OrderProductModel {
            return OrderProductModel(
                    product = orderProduct.product?.let { ProductModel.from(it) } ?: throw IllegalEntityException(),
                    amount = orderProduct.amount,
                    price = orderProduct.price
            )
        }
    }

}