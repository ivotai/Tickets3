package com.unicorn.tickets.ui.act.main

import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.DeviceUtils
import com.jakewharton.rxbinding3.view.clicks
import com.unicorn.tickets.R
import com.unicorn.tickets.app.*
import com.unicorn.tickets.data.event.ScanTicketCodeEvent
import com.unicorn.tickets.ui.act.checkTicket.ScanTicketCodeAct
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.act_scan_ticket_code.*
import org.joda.time.DateTime

class CheckinTicketAct : ScanTicketCodeAct() {

    override fun initViews() {
        fun displayInfo() {
            titleBar.setTitle(title = "辰山植物园检票系统")
            tvVersionName.text = "程序版本：${AppUtils.getAppVersionName()}"
            tvUsername.text = "登录用户：${Global.loginResponse.user.username}"
            tvLoginTime.text = "登录时间：${DateTime().toString(Configs.displayDateFormat2)}"
            tvAndroidId.text = "设备 ID：${DeviceUtils.getAndroidID()}"
        }
        displayInfo()
    }

    override fun bindIntent() {
        super.bindIntent()
        ivSignOut.clicks().mergeWith(tvSignOut.clicks()).subscribe { finish() }
        llScanTicketCode.safeClicks().subscribe { scanTicketCode() }

        if (Global.roleTag == Place) {
            tvScanTicketCode.text = "景点预约核检"
        }

        btnCheckIdCard.safeClicks().subscribe {
            startCheckCard()
        }
    }

    override fun registerEvent() {
        RxBus.registerEvent(this, ScanTicketCodeEvent::class.java, Consumer {
            scanTicketCode()
        })
    }

    override val layoutId = R.layout.act_scan_ticket_code

}