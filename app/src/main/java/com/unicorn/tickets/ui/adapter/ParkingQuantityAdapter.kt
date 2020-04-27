package com.unicorn.tickets.ui.adapter

import android.graphics.drawable.GradientDrawable
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.ConvertUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.unicorn.tickets.R
import com.unicorn.tickets.app.RxBus
import com.unicorn.tickets.app.safeClicks
import com.unicorn.tickets.data.model.CarQuantity
import com.unicorn.tickets.data.model.ParkingQuantitiy
import com.unicorn.tickets.ui.base.KVHolder
import kotlinx.android.synthetic.main.item_parking_quantitiy.*

class ParkingQuantityAdapter : BaseQuickAdapter<ParkingQuantitiy, KVHolder>(R.layout.item_car_quantitiy) {

    override fun convert(helper: KVHolder, item: ParkingQuantitiy) {
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