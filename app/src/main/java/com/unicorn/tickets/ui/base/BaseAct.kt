package com.unicorn.tickets.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.unicorn.tickets.app.di.ComponentHolder

abstract class BaseAct : AppCompatActivity(), UI {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        inject()
        initViews()
        bindIntent()
        registerEvent()
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