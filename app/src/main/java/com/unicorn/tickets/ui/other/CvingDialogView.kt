package com.unicorn.tickets.ui.other

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.afollestad.materialdialogs.MaterialDialog
import com.unicorn.tickets.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.dialog_cving.view.*
import kotlinx.android.synthetic.main.dialog_print_success.view.tvPrompt1

class CvingDialogView(context: Context, cv: String) : FrameLayout(context), LayoutContainer {

    lateinit var dialog: MaterialDialog

    init {
        LayoutInflater.from(context).inflate(R.layout.dialog_cving, this, true)
        tvPrompt1.text = "正在$cv,请扫码"

        val c = cv == "检票"
        tvPrompt1.setTextColor( Color.parseColor(if(c) "#498FE2" else "#F68B2D"))
        ivImage.setImageResource(if (c) R.mipmap.c else R.mipmap.v)

    }

    override val containerView = this

}