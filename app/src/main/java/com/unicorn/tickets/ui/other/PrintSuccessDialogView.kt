package com.unicorn.tickets.ui.other

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.afollestad.materialdialogs.MaterialDialog
import com.unicorn.tickets.R
import com.unicorn.tickets.app.Configs
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.dialog_print_success.view.*
import org.joda.time.DateTime

class PrintSuccessDialogView(context: Context) : FrameLayout(context), LayoutContainer {

    lateinit var dialog: MaterialDialog


    init {
        LayoutInflater.from(context).inflate(R.layout.dialog_print_success, this, true)

        tvPrompt3.text = DateTime().toString(Configs.displayDateFormat2)
    }

    override val containerView = this

}