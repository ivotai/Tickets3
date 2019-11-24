package com.unicorn.tickets.data.model

import com.blankj.utilcode.util.DeviceUtils
import com.unicorn.tickets.app.Global

data class CheckinTicketParam(
    val gateType: Int = 1,
    val checkerId: Long = Global.loginResponse.user.id,
    val gateTag: String = DeviceUtils.getAndroidID(),
    val ticketCode: String,
    val checkerName: String = Global.loginResponse.user.username
)