package com.unicorn.tickets.ui.act

import com.unicorn.tickets.R
import com.unicorn.tickets.app.*
import com.unicorn.tickets.app.helper.DialogHelper
import com.unicorn.tickets.app.helper.ExceptionHelper
import com.unicorn.tickets.data.event.PrintHelperPreparedEvent
import com.unicorn.tickets.data.model.Order
import com.unicorn.tickets.data.model.RefundTicketParam
import com.unicorn.tickets.data.model.SourceType
import com.unicorn.tickets.ui.base.PrintAct
import io.reactivex.functions.Consumer
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.act_order_detail.*
import org.joda.time.DateTime

class OrderDetailAct : PrintAct() {

    override val layoutId = R.layout.act_order_detail

    override fun initViews() {

        fun getOrder() {
            fun displayOrder(order: Order) {
                with(order) {
                    tvStatusText.text = statusText
                    tvTotalPrice.text = "￥$totalPrice"
//                    tvTitle2.text = "$title $orderId"
                    tvTitle2.text = "$orderId"
                    tvTravelDate.text =
                        "使用日期：${DateTime(travelDate).toString(Configs.displayDateFormat)}"
                    tvCreateDate.text =
                        "预定日期：${DateTime(createdDate).toString(Configs.displayDateFormat)}"
                    tvChannel.text = "购买渠道：${SourceType.sourceTypeMap[sourceType]}"
                }
            }

            val orderId = intent.getLongExtra(Key.OrderId, 0)
            val mask = DialogHelper.showMask(this)
            api.getTicketOrderInfo(orderId)
                .observeOnMain(this)
                .subscribeBy(
                    onSuccess = {
                        order = it
                        displayOrder(it)
                        mask.dismiss()
                    },
                    onError = {
                        mask.dismiss()
                        ExceptionHelper.showPrompt(it)
                    }
                )
        }

        titleBar.setTitle("订单详情")
        getOrder()
    }

    private fun refundTicket() {
        val mask = DialogHelper.showMask(this)
        api.refundTicket(RefundTicketParam(order.orderId))
            .observeOnMain(this)
            .subscribeBy(
                onSuccess = {
                    mask.dismiss()
                    if (it.failed) return@subscribeBy
                    "退票成功".toast()
                    finish()
                },
                onError = {
                    mask.dismiss()
                    ExceptionHelper.showPrompt(it)
                }
            )
    }

    override fun bindIntent() {
        super.bindIntent()
//        rtvRefundTicket.safeClicks().subscribe {
//            refundTicket()
//        }
        rtvPrintTicket.safeClicks().subscribe {
            if (printHelperPrepared)
                takeTicket(order.orderId)
        }
    }

    override fun registerEvent() {
        RxBus.registerEvent(this, PrintHelperPreparedEvent::class.java, Consumer {
            printHelperPrepared = true
        })
    }

    private var printHelperPrepared = false

    private lateinit var order: Order

}