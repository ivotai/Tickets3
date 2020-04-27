package com.unicorn.tickets.data.model

import com.blankj.utilcode.util.DeviceUtils
import com.unicorn.tickets.app.Global

data class PayParkingOrderParam(
    val authCode: String,
    val conductorId: Long = Global.loginResponse.user.id,
    val quantity: Int,
    val terminalEquipmentTag: String = DeviceUtils.getAndroidID()
)