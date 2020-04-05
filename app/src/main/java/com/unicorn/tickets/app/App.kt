package com.unicorn.tickets.app

import androidx.multidex.MultiDexApplication
import com.chibatching.kotpref.Kotpref
import com.facebook.stetho.Stetho
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.unicorn.tickets.app.helper.ExceptionHelper
import io.reactivex.exceptions.OnErrorNotImplementedException
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins

class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        Logger.addLogAdapter(AndroidLogAdapter())
        Stetho.initializeWithDefaults(this)
        Kotpref.init(this)
        init()
    }

    private fun init() {
        RxJavaPlugins.setErrorHandler { throwable ->
            if (throwable is UndeliverableException || throwable.cause is UndeliverableException) {
                return@setErrorHandler
            }
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