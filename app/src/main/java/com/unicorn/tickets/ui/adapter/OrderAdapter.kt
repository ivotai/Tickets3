package com.unicorn.tickets.ui.adapter

import android.content.Intent
import com.chad.library.adapter.base.BaseQuickAdapter
import com.unicorn.tickets.R
import com.unicorn.tickets.app.Configs
import com.unicorn.tickets.app.Key
import com.unicorn.tickets.app.safeClicks
import com.unicorn.tickets.data.model.Order
import com.unicorn.tickets.ui.act.OrderDetailAct
import com.unicorn.tickets.ui.base.KVHolder
import kotlinx.android.synthetic.main.item_order.*
import org.joda.time.DateTime

class OrderAdapter : BaseQuickAdapter<Order, KVHolder>(R.layout.item_order) {

    override fun convert(helper: KVHolder, item: Order) {
        helper.apply {
            tvOrderId.text = "订单号：${item.orderId}"
            tvTitle.text = item.title
            tvTotalPrice.text = "￥${item.totalPrice}"
            tvDescription.text =
                "${DateTime(item.travelDate).toString(Configs.displayDateFormat)} 有效 ${item.quantity}张"
            tvStatus.text = item.statusText

            root.safeClicks().subscribe {
                Intent(context, OrderDetailAct::class.java).apply {
                    putExtra(Key.OrderId,item.orderId)
                }.let { context.startActivity(it) }
            }
        }
    }

}