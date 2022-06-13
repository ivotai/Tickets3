package com.unicorn.tickets.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.unicorn.tickets.app.RxBus
import com.unicorn.tickets.app.di.ComponentHolder
import com.unicorn.tickets.data.event.Logout
import com.unicorn.tickets.ui.act.main.LoginAct
import io.reactivex.functions.Consumer

abstract class BaseAct : AppCompatActivity(), UI {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        inject()
        initViews()
        bindIntent()
        registerEvent()

        // 给所有 Act 注入 logout
        val isLoginAct = this is LoginAct
        if (!isLoginAct) {
            RxBus.registerEvent(this, Logout::class.java, Consumer {
                finish()
            })
        }
    }

    override fun inject() {
    }

    override fun initViews() {
    }

    override fun bindIntent() {
    }

    override fun registerEvent() {

    }

    protected val api = ComponentHolder.appComponent.api()

    protected val checkApi = ComponentHolder.appComponent.checkApi()

}