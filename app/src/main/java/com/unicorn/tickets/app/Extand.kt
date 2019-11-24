package com.unicorn.tickets.app

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ToastUtils
import com.jakewharton.rxbinding3.view.clicks
import com.orhanobut.logger.Logger
import com.unicorn.tickets.R
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration
import florent37.github.com.rxlifecycle.RxLifecycle.disposeOnDestroy
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

fun View.safeClicks(): Observable<Unit> = this.clicks()
    .throttleFirst(2000, TimeUnit.MILLISECONDS)

fun TextView.trimText() = this.text.toString().trim()

fun <T> Single<T>.observeOnMain(lifecycleOwner: LifecycleOwner): Single<T> =
    this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .compose(disposeOnDestroy(lifecycleOwner))

fun <T> Observable<T>.observeOnMain(lifecycleOwner: LifecycleOwner): Observable<T> =
    this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .compose(disposeOnDestroy(lifecycleOwner))

fun RecyclerView.addDefaultItemDecoration(size: Int) {
    HorizontalDividerItemDecoration.Builder(context)
        .colorResId(R.color.md_grey_300)
        .size(size)
        .build().let { this.addItemDecoration(it) }
}

fun Context.startAct(actClass: Class<*>) {
    this.startActivity(Intent(this, actClass))
}

fun Context.toActAndFinish(actClass: Class<*>) {
    this.startActivity(Intent(this, actClass))
    this as Activity
    this.finish()
}

fun Throwable.print() {
    Logger.e(this.toString())
}

fun EditText.isEmpty(): Boolean {
    return this.trimText().isEmpty()
}

fun String.toast() {
    ToastUtils.showShort(this)
}
