package com.unicorn.tickets.app

import androidx.multidex.MultiDexApplication
import com.facebook.stetho.Stetho
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.unicorn.tickets.app.helper.ExceptionHelper
import io.reactivex.exceptions.OnErrorNotImplementedException
import io.reactivex.plugins.RxJavaPlugins

class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        Logger.addLogAdapter(AndroidLogAdapter())
        Stetho.initializeWithDefaults(this)
        init()
    }

    private fun init() {
        RxJavaPlugins.setErrorHandler { throwable ->
            when (throwable) {
                is OnErrorNotImplementedException -> throwable.cause?.let {
                    ExceptionHelper.showPrompt(
                        it
                    )
                }
                else -> throwable.toString().toast()
            }
        }
    }

}