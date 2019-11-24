package com.unicorn.tickets.ui.act.car

import androidx.viewpager.widget.ViewPager
import com.unicorn.tickets.R
import com.unicorn.tickets.app.RxBus
import com.unicorn.tickets.data.event.CurrentItem
import com.unicorn.tickets.data.model.PayCarOrderResponse
import com.unicorn.tickets.ui.adapter.pager.CarTicketScanPagerAdapter
import com.unicorn.tickets.ui.base.BaseAct
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.act_car_ticket_scan.*

class CarTicketScanAct : BaseAct() {
    companion object {
        var quantity = 0
        var payCarOrderResponse:PayCarOrderResponse? = null
    }

    override val layoutId = R.layout.act_car_ticket_scan

    override fun initViews() {
        titleBar.setTitle("车票扫码")
        stepView.setSteps(CarTicketScanPagerAdapter.titles)

        viewPaper.offscreenPageLimit = CarTicketScanPagerAdapter.titles.size - 1
        viewPaper.adapter = CarTicketScanPagerAdapter(supportFragmentManager)
        viewPaper.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }


            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                stepView.go(position, false)
            }
        })

    }

    override fun registerEvent() {
        RxBus.registerEvent(this, CurrentItem::class.java, Consumer {
            viewPaper.currentItem = it.position
        })
    }

}