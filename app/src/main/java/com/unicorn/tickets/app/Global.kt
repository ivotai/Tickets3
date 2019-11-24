package com.unicorn.tickets.app

import com.unicorn.tickets.data.model.GroupApply
import com.unicorn.tickets.data.model.LoginResponse

object Global {

    val session: String get() = loginResponse.session
    val token: String get() = loginResponse.loginToken

    lateinit var loginResponse: LoginResponse

    lateinit var groupApply: GroupApply

}