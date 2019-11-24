package com.unicorn.tickets.app.helper

import com.unicorn.tickets.app.toast
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ExceptionHelper {

    fun getPrompt(throwable: Throwable) = when (throwable) {
        is UnknownHostException -> "是不是没联网？"
        is SocketTimeoutException -> "超时了"
        is HttpException -> when (throwable.code()) {
            500 -> "服务器内部错误"
            else -> "错误码${throwable.code()}"
        }
        else -> throwable.toString()
    }

    fun showPrompt(throwable: Throwable) {
        getPrompt(throwable).toast()
    }

}