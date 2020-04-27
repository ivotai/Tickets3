package com.unicorn.tickets.data.model

import com.unicorn.tickets.app.Global

data class RefundParkingOrderParam(
    val conductorId: Long = Global.loginResponse.user.id,
    val orderId: Long
)