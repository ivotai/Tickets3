package com.unicorn.tickets.ui.act.buyTicket

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.ConvertUtils
import com.unicorn.tickets.R
import com.unicorn.tickets.app.safeClicks
import com.unicorn.tickets.ui.base.BaseAct
import kotlinx.android.synthetic.main.act_pay_success.*

class PayFailedAct : BaseAct() {
    private val md400 by lazy { ContextCompat.getColor(this, R.color.md_grey_300) }

    override val layoutId = R.layout.act_pay_failed

    override fun initViews() {
        titleBar.setTitle("售票确认")
        tvPrompt.background = GradientDrawable().apply {
            val width = ConvertUtils.dp2px(1f)
            val dashWidth = ConvertUtils.dp2px(4f).toFloat()
            val dashGap = ConvertUtils.dp2px(4f).toFloat()
            setStroke(width, md400, dashWidth, dashGap)
            setColor(Color.WHITE)
        }
    }

    override fun bindIntent() {
        super.bindIntent()
        rtvBack.safeClicks().subscribe { finish() }
    }

}