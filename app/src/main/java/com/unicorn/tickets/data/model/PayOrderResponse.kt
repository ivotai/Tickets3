package com.unicorn.tickets.data.model

import java.io.Serializable

data class PayOrderResponse(
    val orderId: Long,
    val amount: Double,
    val payTypeText: String
) : Serializable