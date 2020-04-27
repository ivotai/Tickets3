package com.unicorn.tickets.ui.adapter

import android.graphics.drawable.GradientDrawable
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.ConvertUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.unicorn.tickets.R
import com.unicorn.tickets.app.RxBus
import com.unicorn.tickets.app.safeClicks
import com.unicorn.tickets.data.model.ParkingQuantity
import com.unicorn.tickets.ui.base.KVHolder
import kotlinx.android.synthetic.main.item_parking_quantitiy.*

class ParkingQuantityAdapter :
    BaseQuickAdapter<ParkingQuantity, KVHolder>(R.layout.item_car_quantitiy) {

    fun defaultSelectOne() {
        data.forEachIndexed { index, parkingQuantity ->
            parkingQuantity.isSelected = index == 0
        }
        notifyDataSetChanged()
        RxBus.post(data[0])
    }

    override fun convert(helper: KVHolder, item: ParkingQuantity) {
        helper.apply {
            rtvQuantity.text = item.quantity.toString()
            val color = ContextCompat.getColor(
                mContext,
                if (item.isSelected) R.color.colorPrimary else R.color.md_grey_500
            )
            rtvQuantity.setTextColor(color)
            rtvQuantity.background = GradientDrawable().apply {
                setStroke(ConvertUtils.dp2px(2f), color)
            }

            root.safeClicks().subscribe {
                data.forEach {
                    it.isSelected = it == item
                }
                notifyDataSetChanged()
                RxBus.post(item)
            }
        }
    }

}