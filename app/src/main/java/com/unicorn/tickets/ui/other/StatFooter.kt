package com.unicorn.tickets.ui.other

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.unicorn.tickets.R
import kotlinx.android.extensions.LayoutContainer

class StatFooter(context: Context) : FrameLayout(context), LayoutContainer {

    init {
        LayoutInflater.from(context).inflate(R.layout.footer_stat, this, true)
    }

    override val containerView = this

}