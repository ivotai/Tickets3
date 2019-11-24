package com.unicorn.tickets.data.model.base

import com.unicorn.tickets.app.RxBus
import com.unicorn.tickets.data.event.ProductQuantityChange

data class GroupProduct(
    val category: Int,
    val content: String,
    val createdDate: Long,
    val defaultPrice: Double,
    val identityCertificateType: Int,
    val lastUpdateDate: Long,
    val name: String,
    val objectId: String,
    val orderNo: Int,
    val peopleCount: Int,
    val requiredIdentityCertificate: Int,
    val stype: Int,
    val terminalPda: Int
){
    // 这个属性我加的
    var quantity: Int = 0
        set(value) {
            field = value
            RxBus.post(ProductQuantityChange())
        }
}