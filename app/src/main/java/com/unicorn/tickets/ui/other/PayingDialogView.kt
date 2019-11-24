package com.unicorn.tickets.ui.other

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.lifecycle.LifecycleOwner
import com.afollestad.materialdialogs.MaterialDialog
import com.blankj.utilcode.util.ToastUtils
import com.unicorn.tickets.R
import com.unicorn.tickets.app.di.ComponentHolder
import com.unicorn.tickets.app.observeOnMain
import com.unicorn.tickets.app.safeClicks
import com.unicorn.tickets.data.model.CancelPayParam
import io.reactivex.disposables.Disposable
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.dialog_paying.view.*

class PayingDialogView(context: Context, orderId: Long) : FrameLayout(context), LayoutContainer {

    lateinit var dialog: MaterialDialog
    lateinit var disposable: Disposable

    init {
        LayoutInflater.from(context).inflate(R.layout.dialog_paying, this, true)

        tvCancelPay.safeClicks().subscribe { cancelPay(orderId) }
    }

    private fun cancelPay(orderId: Long) {
        disposable.dispose()
        dialog.dismiss()
        api.cancelPay(CancelPayParam(orderId))
            .observeOnMain(context as LifecycleOwner)
            .subscribe {
                if (it.failed) return@subscribe
                ToastUtils.showShort("支付已取消")
            }
    }

    private val api = ComponentHolder.appComponent.api()
    override val containerView = this

}