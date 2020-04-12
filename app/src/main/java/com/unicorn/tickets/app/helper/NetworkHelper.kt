package com.unicorn.tickets.app.helper

import com.unicorn.tickets.app.Configs
import com.unicorn.tickets.app.Global
import com.unicorn.tickets.app.Key
import com.unicorn.tickets.app.di.ComponentHolder
import okhttp3.Interceptor
import okhttp3.Response

object NetworkHelper {

    fun proceedRequestWithNewSession(chain: Interceptor.Chain): Response {
        // session 过期时使用 token 登录，获取新的 session 和 token。
        api.loginSilently().execute().body().let { Global.loginResponse = it!! }
        return proceedRequestWithSession(chain)
    }

    fun proceedRequestWithSession(chain: Interceptor.Chain): Response {
        return chain.request().newBuilder()
            .removeHeader(Key.Cookie)
            .addHeader(Key.Cookie, "${Key.SESSION}=${Global.session}")
            .build()
            .let { chain.proceed(it) }
    }

    fun closeConnection(chain: Interceptor.Chain): Response {
        return chain.request().newBuilder()
            .removeHeader("Connection")
            .addHeader("Connection", "close")
            .build()
            .let { chain.proceed(it) }
    }

    private val api by lazy { ComponentHolder.appComponent.api() }

}