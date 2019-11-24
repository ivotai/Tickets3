package com.unicorn.tickets.data.model

data class PayCarOrderResponse(
    val payTypeText: String,
    val success: Boolean,
    val totalAmount: Double
)