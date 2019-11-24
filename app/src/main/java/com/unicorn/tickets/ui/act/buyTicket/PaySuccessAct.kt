package com.unicorn.tickets.ui.act.buyTicket

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.ConvertUtils
import com.unicorn.tickets.R
import com.unicorn.tickets.app.Key
import com.unicorn.tickets.app.RxBus
import com.unicorn.tickets.app.safeClicks
import com.unicorn.tickets.data.event.PrintHelperPreparedEvent
import com.unicorn.tickets.data.model.PayOrderResponse
import com.unicorn.tickets.ui.base.PrintAct
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.act_pay_success.*

class PaySuccessAct : PrintAct() {

    private val payOrderResponse by lazy { intent.getSerializableExtra(Key.PayOrderResponse) as PayOrderResponse }
    private val md400 by lazy { ContextCompat.getColor(this, R.color.md_grey_300) }

    override fun initViews() {
        titleBar.setTitle("售票确认")
        tvPrompt.background = GradientDrawable().apply {
            val width = ConvertUtils.dp2px(1f)
            val dashWidth = ConvertUtils.dp2px(4f).toFloat()
            val dashGap = ConvertUtils.dp2px(4f).toFloat()
            setStroke(width, md400, dashWidth, dashGap)
            setColor(Color.WHITE)
        }

        with(payOrderResponse) {
            tvTotalPrice.text = "￥$amount"
            tvPayType.text = payTypeText
        }
    }

    override fun bindIntent() {
        super.bindIntent()
//        takeTicket(payOrderResponse.orderId)
        rtvBack.safeClicks().subscribe { finish() }
    }

    override fun registerEvent() {
       RxBus.registerEvent(this,PrintHelperPreparedEvent::class.java, Consumer {
           takeTicket(payOrderResponse.orderId)
       })
    }

    override val layoutId = R.layout.act_pay_success

}