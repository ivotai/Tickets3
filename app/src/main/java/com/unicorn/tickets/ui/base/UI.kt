package com.unicorn.tickets.ui.base

interface UI {

    val layoutId: Int

    fun inject()

    fun initViews()

    fun bindIntent()

    fun registerEvent()

}