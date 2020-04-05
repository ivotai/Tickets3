package com.unicorn.tickets.app.helper

import android.app.Activity
import android.content.Context
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.kaopiz.kprogresshud.KProgressHUD
import com.unicorn.tickets.ui.other.CvingDialogView
import com.unicorn.tickets.ui.other.PayTypeDialogView
import com.unicorn.tickets.ui.other.PrintSuccessDialogView
import com.unicorn.tickets.ui.other.PrintingDialogView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

object DialogHelper {

    fun showMask(context: Context): KProgressHUD {
        return KProgressHUD.create(context)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setCancellable(true)
            .setDimAmount(0.5f)
            .show()
    }

    fun showPayTypeDialog(context: Context, totalPrice: Double) {
        val view = PayTypeDialogView(context, totalPrice)
        view.dialog = createDialog(context, view)
        view.dialog.show()
    }

    fun showPrintSuccessDialog(context: Context) {
        val view = PrintSuccessDialogView(context)
        view.dialog = createDialog(context, view)
        view.dialog.show()
    }

    fun showScaningDialog(context: Context) {
        val view = CvingDialogView(context, "检票")
        view.dialog = createDialog(context, view)
        view.dialog.show()
        Observable.timer(2, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe {
            val a = context as Activity
            if (!a.isDestroyed)
                view.dialog.dismiss()
        }
    }

    fun showPrintingDialog(context: Context, total: Int) {
        val view = PrintingDialogView(context, total)
        view.dialog = createDialog(context, view)
        view.dialog.show()
    }

    private fun createDialog(context: Context, view: View): MaterialDialog {
        return MaterialDialog(context)
            .customView(view = view, noVerticalPadding = true)
            .cancelOnTouchOutside(true)
            .cornerRadius(8f)
    }

}