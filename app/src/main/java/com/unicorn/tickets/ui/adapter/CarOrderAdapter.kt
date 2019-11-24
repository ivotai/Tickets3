package com.unicorn.tickets.ui.adapter

import android.graphics.Color
import androidx.core.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.unicorn.tickets.R
import com.unicorn.tickets.app.Configs
import com.unicorn.tickets.app.RxBus
import com.unicorn.tickets.app.safeClicks
import com.unicorn.tickets.data.model.CarOrder
import com.unicorn.tickets.data.model.RefundCarParam
import com.unicorn.tickets.ui.base.KVHolder
import kotlinx.android.synthetic.main.item_car_order.*
import org.joda.time.DateTime

class CarOrderAdapter : BaseQuickAdapter<CarOrder, KVHolder>(R.layout.item_car_order) {

    override fun convert(helper: KVHolder, item: CarOrder) {
        helper.apply {
            tvOrderId.text = item.orderId.toString()

            tvDate.text = DateTime(item.createTime).toString("HH:mm:ss")
            tvTotalAmount.text = "${item.totalAmount}"
            tvStatus.text = item.statusText
            if (item.refundStatus != -1) {
                tvStatus.text = item.refundStatusText
            }

            val index = data.indexOf(item)
            val flag = index % 2 == 0
            root.setBackgroundColor(if (flag) Color.WHITE else Color.parseColor("#FEF6E9"))

            if (item.status == 1 && item.refundStatus == -1)
                tvStatus.text = "点击退款"
            tvStatus.safeClicks().subscribe {
                if (item.status == 1 && item.refundStatus == -1) RxBus.post(RefundCarParam(orderId = item.orderId))
            }

        }
    }

}