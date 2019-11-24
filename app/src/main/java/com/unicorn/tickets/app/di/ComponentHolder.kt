package com.unicorn.tickets.app.di

import com.unicorn.tickets.app.di.component.AppComponent
import com.unicorn.tickets.app.di.component.DaggerAppComponent

object ComponentHolder {

    val appComponent: AppComponent by lazy { DaggerAppComponent.create() }

}