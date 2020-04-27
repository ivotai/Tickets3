package com.unicorn.tickets.ui.act.parking

import androidx.viewpager.widget.ViewPager
import com.unicorn.tickets.R
import com.unicorn.tickets.app.RxBus
import com.unicorn.tickets.data.event.CurrentItem
import com.unicorn.tickets.ui.adapter.pager.CarTicketScanPagerAdapter
import com.unicorn.tickets.ui.adapter.pager.ParkingScanPagerAdapter
import com.unicorn.tickets.ui.base.BaseAct
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.act_parking_scan.*

class ParkingScanAct : BaseAct() {

    companion object {
        var quantity = 0
    }

    override fun initViews() {
        titleBar.setTitle("停车扫码")
        stepView.setSteps(ParkingScanPagerAdapter.titles)

        viewPaper.offscreenPageLimit = ParkingScanPagerAdapter.titles.size - 1
        viewPaper.adapter = ParkingScanPagerAdapter(supportFragmentManager)
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

    override val layoutId = R.layout.act_parking_scan

}