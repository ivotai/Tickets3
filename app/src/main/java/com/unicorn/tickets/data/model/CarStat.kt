package com.unicorn.tickets.data.model

data class CarStat(
    val pay_type: Int,
    val total_amount: Double,
    val travel_date: String
) {
    val payTypeText: String
        get() = when (pay_type) {
            1 -> "支付宝"
            else -> "微信"
        }
}