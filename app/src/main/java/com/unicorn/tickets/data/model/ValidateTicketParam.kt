package com.unicorn.tickets.data.model

import com.blankj.utilcode.util.DeviceUtils
import com.unicorn.tickets.app.Global

data class ValidateTicketParam(
    val apiKey: String = "",
    val clientId: String = DeviceUtils.getAndroidID(),
    val gateTag: String = DeviceUtils.getAndroidID(),
    val requiredCheckinLog: Boolean = true,
    val ticketCode: String,
    val token: String = "",
    val checker: String = Global.loginResponse.user.username
)

