package com.unicorn.tickets.data.model

import com.unicorn.tickets.app.RxBus
import com.unicorn.tickets.data.event.ProductQuantityChange

data class Product(
    val cardType: Int,
    val content: String,
    val date: Long,
    val defaultPrice: Double,
    val description: String,
    val price: Double,
    val productId: String,
    val requireCard: Int,
    val stype: Int,
    val title: String
) {
    // 这个属性我加的
    var quantity: Int = 0
        set(value) {
            field = value
            RxBus.post(ProductQuantityChange())
        }
}
