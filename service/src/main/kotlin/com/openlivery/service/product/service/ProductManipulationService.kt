package com.openlivery.service.product.service

import com.openlivery.service.common.domain.model.AddressInput
import com.openlivery.service.common.repository.AddressRepository
import com.openlivery.service.product.domain.entity.*
import com.openlivery.service.product.domain.input.InsertBrandInput
import com.openlivery.service.product.domain.input.InsertProductInput
import com.openlivery.service.product.domain.input.UpdateBrandInput
import com.openlivery.service.product.domain.input.UpdateProductInput
import com.openlivery.service.product.repository.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import kotlin.collections.HashSet

@Service
@Transactional
class ProductManipulationService(
        private val productRepository: ProductRepository,
        private val brandRepository: BrandRepository,
        private val categoryRepository: CategoryRepository,
        private val catalogProductRepository: CatalogProductRepository,
        private val cartRepository: CartRepository,
        private val addressRepository: AddressRepository
) {

    fun insertProduct(input: InsertProductInput) =
            input.let { Product(it.name, it.basePrice) }
                    .apply {
                        description = input.description
                        itemCode = input.itemCode
                        brand = input.brandId?.let { id ->
                            brandRepository.findById(id)
                                    .orElseThrow { error("") }
                        }
                        categories = input.categoriesIds?.mapTo(HashSet()) { id ->
                            categoryRepository.findById(id)
                                    .orElseThrow { error("") }
                        } ?: mutableSetOf()
                    }
                    .run { productRepository.save(this) }

    fun updateProduct(input: UpdateProductInput) = input
            .let { productRepository.findById(input.id) }
            .orElseThrow { error("") }
            .apply {
                name = input.name
                basePrice = input.basePrice
                description = input.description
                itemCode = input.itemCode
                brand = input.brandId?.let { id ->
                    brandRepository.findById(id)
                            .orElseThrow { error("") }
                }
                categories = input.categoriesIds?.mapTo(HashSet()) { id ->
                    categoryRepository.findById(id)
                            .orElseThrow { error("") }
                } ?: mutableSetOf()
            }
            .run { productRepository.save(this) }

    fun disableProduct(productId: Long) = productRepository.findById(productId)
            .orElseThrow { error("") }
            .apply { active = false }
            .run { productRepository.save(this) }

    fun enableProduct(productId: Long) = productRepository.findById(productId)
            .orElseThrow { error("") }
            .apply { active = true }
            .run { productRepository.save(this) }

    fun deleteProduct(productId: Long) = productRepository.deleteById(productId)


    fun insertBrand(input: InsertBrandInput) = Brand(input.name)
            .run { brandRepository.save(this) }

    fun updateBrand(input: UpdateBrandInput) = input.id
            .let { brandRepository.findById(it) }
            .orElseThrow { error("") }
            .apply { name = input.name }
            .run { brandRepository.save(this) }

    fun disableBrand(brandId: Long) = brandRepository.findById(brandId)
            .orElseThrow { error("") }
            .apply { active = false }
            .run { brandRepository.save(this) }

    fun enableBrand(brandId: Long) = brandRepository.findById(brandId)
            .orElseThrow { error("") }
            .apply { active = true }
            .run { brandRepository.save(this) }

    fun deleteBrand(brandId: Long) = brandRepository.deleteById(brandId)

    fun addProductToCart(cartId: String, productId: Long, amount: Int): Cart {
        val cartProduct = catalogProductRepository.findById(productId).map {
            CartProduct(it.base.id, amount)
        }.orElseThrow { error("") }

        return cartRepository.findById(cartId)
                .orElseGet { Cart(cartId) }
                .apply {
                    products.add(Optional.ofNullable(products.find { it.id == cartProduct.id })
                            .orElse(cartProduct))
                }
                .run { cartRepository.save(this) }
    }

    fun removeProductFromCart(cartId: String, productId: Long) =
            cartId.let { cartRepository.findById(it) }
                    .orElseGet { Cart(cartId) }
                    .apply { products.removeIf { it.id == productId } }
                    .run { cartRepository.save(this) }

    fun increaseCartProductAmount(cartId: String, productId: Long, amount: Int) =
            cartId.let { cartRepository.findById(it) }
                    .orElseGet { Cart(cartId) }
                    .apply {
                        val product = products.find { it.id == productId }
                        if (product != null) product.amount++
                    }
                    .run { cartRepository.save(this) }

    fun decreaseCartProductAmount(cartId: String, productId: Long, amount: Int) =
            cartId.let { cartRepository.findById(it) }
                    .orElseGet { Cart(cartId) }
                    .apply {
                        val product = products.find { it.id == productId }
                        if (product != null && product.amount > 0) {
                            product.amount--
                            products.removeIf { it.amount <= 0 }
                        }
                    }
                    .run { cartRepository.save(this) }

    fun setCartExistingDeliveryAddress(cartId: String, addressId: Long): Cart {
        val address = addressRepository.findById(addressId)
                .map {
                    CartDeliveryAddress(
                            streetNumber = it.streetNumber,
                            streetName = it.streetName,
                            cityName = it.cityName,
                            governingDistrict = it.governingDistrict,
                            country = it.country,
                            additionalInfo = it.additionalInfo,
                            latitude = it.latitude,
                            longitude = it.longitude
                    )
                }
                .orElseThrow { error("") }

        return cartId.let { cartRepository.findById(it) }
                .orElseGet { Cart(cartId) }
                .apply { deliveryAddress = address }
                .run { cartRepository.save(this) }
    }

    fun setCartNewDeliveryAddress(cartId: String, addressInput: AddressInput): Cart {
        val address = CartDeliveryAddress(
                streetNumber = addressInput.streetNumber,
                streetName = addressInput.streetName,
                cityName = addressInput.cityName,
                governingDistrict = addressInput.governingDistrict,
                country = addressInput.country,
                additionalInfo = addressInput.additionalInfo,
                latitude = addressInput.latitude,
                longitude = addressInput.longitude
        )

        return cartId.let { cartRepository.findById(it) }
                .orElseGet { Cart(cartId) }
                .apply { deliveryAddress = address }
                .run { cartRepository.save(this) }
    }

    fun applyCouponToCart(cartId: String, couponCode: String): Cart {
        TODO("")
    }

}