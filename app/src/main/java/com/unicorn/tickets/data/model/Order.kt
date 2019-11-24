package com.unicorn.tickets.data.model

import java.io.Serializable

data class Order(
//    val createdDate: Long,
    val orderId: Long,
    val quantity: Int,
    val status: Int,
    val statusText: String,
    val title: String,
    val totalPrice: Double,
    val travelDate: Long,
    val sourceType:Int,
    val createdDate:Long
): Serializable
