package com.unicorn.tickets.ui.fra

import android.graphics.drawable.GradientDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import cn.iwgang.simplifyspan.SimplifySpanBuild
import cn.iwgang.simplifyspan.unit.SpecialTextUnit
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.ToastUtils
import com.jakewharton.rxbinding3.widget.textChanges
import com.unicorn.tickets.R
import com.unicorn.tickets.app.RxBus
import com.unicorn.tickets.app.safeClicks
import com.unicorn.tickets.data.event.CurrentItem
import com.unicorn.tickets.data.event.PaySuccessEvent
import com.unicorn.tickets.data.model.CarQuantity
import com.unicorn.tickets.ui.act.car.CarTicketScanAct
import com.unicorn.tickets.ui.adapter.CarQuantityAdapter
import com.unicorn.tickets.ui.base.BaseFra
import com.unicorn.tickets.ui.decoration.GridSpanDecoration2
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.act_checkin_ticket_success.*
import kotlinx.android.synthetic.main.fra_car_quantity.*
import kotlinx.android.synthetic.main.fra_car_quantity.tvPrompt

class CarQuantityFra : BaseFra() {

    override val layoutId = R.layout.fra_car_quantity

    override fun initViews() {
        recyclerView.apply {
            layoutManager = GridLayoutManager(context, 5)
            adapter = carQuantityAdapter
            addItemDecoration(GridSpanDecoration2(ConvertUtils.dp2px(16f)))
        }
        carQuantityAdapter.setNewData(
            (1..15).map { CarQuantity(it) }
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
                RxBus.post(CarQuantity(isSelected = true, quantity = it))
            }

        rtvSubmitOrder.safeClicks().subscribe {
            if (CarTicketScanAct.quantity == 0) {
                ToastUtils.showShort("车票数量不能为0")
                return@subscribe
            }
            RxBus.post(CurrentItem(1))
        }
    }

    private val carQuantityAdapter = CarQuantityAdapter()

    override fun registerEvent() {
        RxBus.registerEvent(this, CarQuantity::class.java, Consumer {
            if (it.isSelected) {
                tvDescription.text = "合计：${it.quantity * 2}"
                CarTicketScanAct.quantity = it.quantity
            }
        })
        RxBus.registerEvent(this,PaySuccessEvent::class.java, Consumer {
            etQuantity.setText("")
            carQuantityAdapter.data.forEach { it.isSelected = false }
            carQuantityAdapter.notifyDataSetChanged()
            RxBus.post(CurrentItem(0))
        })
    }

}