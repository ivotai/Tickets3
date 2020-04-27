package com.unicorn.tickets.data.model.base

data class PayParkingOrderResponse(
    val payTypeText: String,
    val success: Boolean,
    val totalAmount: Double
)