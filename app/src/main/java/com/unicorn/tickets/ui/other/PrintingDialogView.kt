package com.unicorn.tickets.ui.other

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.lifecycle.LifecycleOwner
import cn.iwgang.simplifyspan.SimplifySpanBuild
import cn.iwgang.simplifyspan.unit.SpecialTextUnit
import com.afollestad.materialdialogs.MaterialDialog
import com.unicorn.tickets.R
import com.unicorn.tickets.app.RxBus
import com.unicorn.tickets.app.helper.DialogHelper
import com.unicorn.tickets.data.event.PrintTicketEvent
import io.reactivex.functions.Consumer
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.dialog_printing.view.*

class PrintingDialogView(context: Context, total: Int) : FrameLayout(context), LayoutContainer {

    lateinit var dialog: MaterialDialog

    val color = Color.parseColor("#F77321")

    init {
        LayoutInflater.from(context).inflate(R.layout.dialog_printing, this, true)

        val text1 = SimplifySpanBuild("共")
            .append(SpecialTextUnit(total.toString(), color))
            .append("张，已打印")
            .append(SpecialTextUnit("0", color))
            .append("张")
            .build()
        tvPrompt2.text = text1

        val lifecycleOwner = context as LifecycleOwner
        RxBus.registerEvent(lifecycleOwner, PrintTicketEvent::class.java, Consumer {
            val text = SimplifySpanBuild("共")
                .append(SpecialTextUnit(total.toString(), color))
                .append("张，已打印")
                .append(SpecialTextUnit("${it.index + 1}", color))
                .append("张")
                .build()
            tvPrompt2.text = text

            if (it.index + 1 == total) {
                dialog.dismiss()
                DialogHelper.showPrintSuccessDialog(context)
            }
        })
    }

    override val containerView = this

}