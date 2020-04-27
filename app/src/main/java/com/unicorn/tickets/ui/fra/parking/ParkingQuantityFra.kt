package com.unicorn.tickets.ui.fra.parking

import android.graphics.drawable.GradientDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.ToastUtils
import com.jakewharton.rxbinding3.widget.textChanges
import com.unicorn.tickets.R
import com.unicorn.tickets.app.RxBus
import com.unicorn.tickets.app.safeClicks
import com.unicorn.tickets.data.event.CurrentItem
import com.unicorn.tickets.data.event.PaySuccessEvent
import com.unicorn.tickets.data.model.ParkingQuantity
import com.unicorn.tickets.ui.act.parking.ParkingScanAct.Companion.quantity
import com.unicorn.tickets.ui.adapter.ParkingQuantityAdapter
import com.unicorn.tickets.ui.base.BaseFra
import com.unicorn.tickets.ui.decoration.GridSpanDecoration2
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.fra_parking_quantity.*

class ParkingQuantityFra : BaseFra() {

    override fun initViews() {
        recyclerView.apply {
            layoutManager = GridLayoutManager(context, 5)
            adapter = parkingQuantityAdapter
            addItemDecoration(GridSpanDecoration2(ConvertUtils.dp2px(16f)))
        }
        parkingQuantityAdapter.setNewData(
            (1..15).map { ParkingQuantity(it) }
        )
        val color = ContextCompat.getColor(context!!, R.color.md_grey_400)
        etQuantity.background = GradientDrawable().apply {
            setStroke(1, color)
            cornerRadius = ConvertUtils.dp2px(4f).toFloat()
        }
    }

    override fun bindIntent() {
        etQuantity.textChanges()
            .map {
                try {
                    it.toString().toInt()
                } catch (e: Exception) {
                    0
                }
            }
            .subscribe {
                RxBus.post(ParkingQuantity(isSelected = true, quantity = it))
            }

        rtvSubmitOrder.safeClicks().subscribe {
            if (quantity == 0) {
                ToastUtils.showShort("停车数量不能为0")
                return@subscribe
            }
            RxBus.post(CurrentItem(1))
        }
    }

    private val parkingQuantityAdapter = ParkingQuantityAdapter()

    override fun registerEvent() {
        RxBus.registerEvent(this, ParkingQuantity::class.java, Consumer {
            if (it.isSelected) {
                tvDescription.text = "合计：${it.quantity * 10}"
                quantity = it.quantity
            }
        })
        RxBus.registerEvent(this, PaySuccessEvent::class.java, Consumer {
            etQuantity.setText("")
            parkingQuantityAdapter.data.forEach { it.isSelected = false }
            parkingQuantityAdapter.notifyDataSetChanged()
            RxBus.post(CurrentItem(0))

            parkingQuantityAdapter.defaultSelectOne()
        })
        parkingQuantityAdapter.defaultSelectOne()
    }

    override val layoutId = R.layout.fra_parking_quantity

}