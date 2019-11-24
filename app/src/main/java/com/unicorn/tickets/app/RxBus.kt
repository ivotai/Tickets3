package com.unicorn.tickets.app

import androidx.lifecycle.LifecycleOwner
import florent37.github.com.rxlifecycle.RxLifecycle.disposeOnDestroy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

object RxBus {

    private val subject: Subject<Any> = PublishSubject.create<Any>().toSerialized()

    fun post(obj: Any) {
        subject.onNext(obj)
    }

    fun <T> registerEvent(lifecycleOwner: LifecycleOwner, clazz: Class<T>, consumer: Consumer<T>) {
        subject.compose(disposeOnDestroy(lifecycleOwner))
            .ofType(clazz)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(consumer)
    }

}