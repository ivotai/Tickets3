package com.unicorn.tickets.app.di.module

import android.widget.Checkable
import com.unicorn.tickets.app.V1
import com.unicorn.tickets.app.V2
import com.unicorn.tickets.data.api.CheckApi
import com.unicorn.tickets.data.api.SimpleApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named

@Module
class ApiModule {

    @Provides
    fun simpleApi(@Named(V1) retrofit: Retrofit): SimpleApi = retrofit.create(SimpleApi::class.java)

    @Provides
    fun checkApi(@Named(V2) retrofit: Retrofit): CheckApi = retrofit.create(CheckApi::class.java)

}