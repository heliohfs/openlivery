package com.openlivery.service.product

import com.openlivery.service.common.entities.BaseEntity
import java.math.BigDecimal
import javax.persistence.*

@Entity(name = "Product")
@Table(name = "product")
data class Product(
        @Column(name = "product_name")
        var name: String? = null
) : BaseEntity() {

    @Column(name = "base_price")
    var price: BigDecimal = BigDecimal.ZERO

    @Column(name = "description")
    var description: String? = null

    @Column(name = "item_code")
    var itemCode: String? = null

    @Column(name = "picture_storage_key")
    var pictureStorageKey: String? = null

    @ManyToOne
    @JoinColumn(name = "brand_id")
    var brand: Brand? = null

}
