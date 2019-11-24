package com.unicorn.tickets.data.model

data class CarOrder(
    val conductorId: String,
    val conductorName: String,
    val orderId: Long,
    val payType: Int,
    val quantity: Int,
    val status: Int,
    val totalAmount: Double,
    val refundStatus: Int = -1,
    val createTime:Long
) {
    val statusText: String
        get() = when (status) {
            0 -> "待支付"
            1 -> "支付成功"
            else -> "支付失败"
        }
    val refundStatusText: String
        get() = when (status) {
            0 -> "待退款"
            1 -> "退款成功"
            else -> "退款失败"
        }
}
