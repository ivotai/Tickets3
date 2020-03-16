package com.unicorn.tickets.app.di.component

import com.unicorn.tickets.app.di.module.ApiModule
import com.unicorn.tickets.app.di.module.NetworkModule
import com.unicorn.tickets.data.api.CheckApi
import com.unicorn.tickets.data.api.SimpleApi
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, ApiModule::class])
interface AppComponent {

    fun api(): SimpleApi

    fun checkApi():CheckApi

}