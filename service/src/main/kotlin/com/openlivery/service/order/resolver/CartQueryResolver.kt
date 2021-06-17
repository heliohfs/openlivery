package com.openlivery.service.order.resolver

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.openlivery.service.common.auth.IAuthenticationFacade
import com.openlivery.service.order.domain.Cart
import com.openlivery.service.order.domain.dto.OrderCartModel
import com.openlivery.service.order.domain.dto.OrderCartProductModel
import com.openlivery.service.order.domain.enums.DiscountType
import com.openlivery.service.order.service.CartService
import com.openlivery.service.order.service.CatalogProductService
import com.openlivery.service.order.service.CouponService
import org.apache.commons.lang3.tuple.MutablePair
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.util.*

@Component("OrderCartQueryResolver")
class CartQueryResolver(
        private val auth: IAuthenticationFacade,
        private val cartService: CartService,
        private val catalogProductService: CatalogProductService,
        private val couponService: CouponService
) : GraphQLQueryResolver {

    @PreAuthorize("isAuthenticated()")
    fun cart(): OrderCartModel {
        val userId = Optional.ofNullable(auth.user.id)
                .orElseThrow { error("User does not exist.") }

        val cart = cartService.findByUser(userId)
                .orElse(Cart(userId))

        val coupon = cart.appliedCoupon?.let { code ->
            couponService.findByCode(code)
                    .orElseThrow { error("Coupon does not exist or is not active") }
        }

        val orderDecimalDiscount = coupon?.discounts?.maxByOrNull { discount ->
            if (discount.discountType === DiscountType.ORDER) discount.decimalDiscount
            else BigDecimal.ZERO
        }?.decimalDiscount ?: BigDecimal.ZERO

        val deliveryFeeDecimalDiscount = coupon?.discounts?.maxByOrNull { discount ->
            if (discount.discountType === DiscountType.DELIVERY_FEE) discount.decimalDiscount
            else BigDecimal.ZERO
        }?.decimalDiscount ?: BigDecimal.ZERO

        val (productModel, totalValue) = cart.products.fold(MutablePair(mutableSetOf<OrderCartProductModel>(), BigDecimal.ZERO))
        { acc, cartProduct ->
            val catalogProduct = catalogProductService.findById(cartProduct.id)
                    .orElseThrow { error("Product not found") }

            val decimalDiscount = if (!catalogProduct.discountApplied) {
                coupon?.discounts?.find { it.product?.id == catalogProduct.id }?.decimalDiscount ?: BigDecimal.ZERO
            } else BigDecimal.ZERO

            val productModel = OrderCartProductModel(
                    id = catalogProduct.id,
                    finalPrice = catalogProduct.finalPrice.subtract(catalogProduct.finalPrice.multiply(decimalDiscount)),
                    basePrice = catalogProduct.basePrice,
                    pictureStorageKey = catalogProduct.pictureStorageKey,
                    amount = cartProduct.amount,
                    discountApplied = catalogProduct.discountApplied
            )

            val price = catalogProduct.finalPrice.multiply(BigDecimal(cartProduct.amount))

            acc.left.add(productModel)
            acc.right = acc.right.add(price.subtract(price.multiply(decimalDiscount)))
            acc
        }

        return OrderCartModel(
                totalValue = totalValue.subtract(totalValue.multiply(orderDecimalDiscount)),
                deliveryFee = BigDecimal.ZERO.subtract(BigDecimal.ZERO.multiply(deliveryFeeDecimalDiscount)),
                products = productModel,
                couponApplied = cart.appliedCoupon
        )
    }

}