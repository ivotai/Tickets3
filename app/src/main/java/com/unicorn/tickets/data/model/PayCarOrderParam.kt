package com.unicorn.tickets.data.model

import com.blankj.utilcode.util.DeviceUtils
import com.unicorn.tickets.app.Global

data class PayCarOrderParam(
    val authCode: String,
    val conductorId: Long = Global.loginResponse.user.id,
    val quantity: Int,
    val terminalEquipmentTag: String = DeviceUtils.getAndroidID(),
    val category: Int    // 1 按量计费 2 随便乘
)