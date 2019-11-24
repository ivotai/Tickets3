package com.unicorn.tickets.data.model

data class Ticket(
    val buyTime: Long,
    val ticketCode: String,
    val quantity: Int,
    val stype: Int,
    val ticketId: String,
    val title: String,
    val travelDate: Long,
    val price: Double,
    val sourceType:Int,
    val ticketNo:String
)