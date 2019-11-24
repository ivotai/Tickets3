package com.unicorn.tickets.data.model

data class PayOrderParam(
    val orderId: Long,
    val payType: Int,
    val authCode: String
)