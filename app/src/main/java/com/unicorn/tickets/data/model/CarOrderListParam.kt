package com.unicorn.tickets.data.model

import com.blankj.utilcode.util.DeviceUtils
import com.unicorn.tickets.app.Global
import java.util.*

data class CarOrderListParam(
    val driverId:Long = Global.loginResponse.user.id,
    val terminalEquipmentTag:String = DeviceUtils.getAndroidID(),
    val travelDate: Date = Date()
)