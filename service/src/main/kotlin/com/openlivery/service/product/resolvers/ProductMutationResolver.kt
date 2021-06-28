package com.openlivery.service.product.resolvers

import com.openlivery.service.common.auth.AuthProvider
import com.openlivery.service.common.domain.entity.Authority
import com.openlivery.service.common.domain.model.AddressInput
import com.openlivery.service.product.domain.input.InsertProductInput
import com.openlivery.service.product.domain.input.UpdateProductInput
import com.openlivery.service.product.domain.model.CartModel
import com.openlivery.service.product.domain.model.ProductModel
import com.openlivery.service.product.service.CartService
import com.openlivery.service.product.service.ProductManipulationService
import graphql.kickstart.tools.GraphQLMutationResolver
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component

@Component
class ProductMutationResolver(
        private val auth: AuthProvider,
        private val productManipulationService: ProductManipulationService,
        private val cartService: CartService
) : GraphQLMutationResolver {

    @PreAuthorize("hasAuthority('${Authority.CREATE_PRODUCT}')")
    fun insertProduct(input: InsertProductInput): ProductModel {
        return productManipulationService.insertProduct(input)
                .let { ProductModel.from(it) }
    }

    @PreAuthorize("hasAuthority('${Authority.UPDATE_PRODUCT}')")
    fun updateProduct(input: UpdateProductInput): ProductModel {
        return productManipulationService.updateProduct(input)
                .let { ProductModel.from(it) }
    }

    @PreAuthorize("hasAuthority('${Authority.UPDATE_PRODUCT}')")
    fun disableProduct(productId: Long): ProductModel {
        return productManipulationService.disableProduct(productId)
                .let { ProductModel.from(it) }
    }

    @PreAuthorize("hasAuthority('${Authority.UPDATE_PRODUCT}')")
    fun enableProduct(productId: Long): ProductModel {
        return productManipulationService.enableProduct(productId)
                .let { ProductModel.from(it) }
    }

    @PreAuthorize("hasAuthority('${Authority.DELETE_PRODUCT}')")
    fun deleteProduct(productId: Long): Boolean {
        productManipulationService.deleteProduct(productId)
        return true
    }

    @PreAuthorize("permitAll()")
    fun addProductToCart(productId: Long, amount: Int): CartModel {
        val cartId = auth.id
        productManipulationService.addProductToCart(cartId, productId, amount)
        return cartService.getCart(cartId)
                .let { CartModel.from(it) }
    }

    @PreAuthorize("permitAll()")
    fun removeProductFromCart(productId: Long): CartModel {
        val cartId = auth.id
        TODO("")
    }

    @PreAuthorize("permitAll()")
    fun increaseCartProductAmount(productId: Long, amount: Int): CartModel {
        val cartId = auth.id
        TODO("")
    }

    @PreAuthorize("permitAll()")
    fun decreaseCartProductAmount(productId: Long, amount: Int): CartModel {
        val cartId = auth.id
        TODO("")
    }

    @PreAuthorize("permitAll()")
    fun setCartExistingDeliveryAddress(addressId: Long): CartModel {
        val cartId = auth.id
        TODO("")
    }

    @PreAuthorize("permitAll()")
    fun setCartNewDeliveryAddress(address: AddressInput): CartModel {
        val cartId = auth.id
        TODO("")
    }

    @PreAuthorize("permitAll()")
    fun applyCoupon(couponCode: String): CartModel {
        val cartId = auth.id
        TODO("")
    }

    @PreAuthorize("permitAll()")
    fun removeCoupon(): CartModel {
        val cartId = auth.id
        TODO("")
    }

    @PreAuthorize("permitAll()")
    fun clearCart(): CartModel {
        val cartId = auth.id
        TODO("")
    }

}