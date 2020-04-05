package com.unicorn.tickets.ui.act.main

import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.DeviceUtils
import com.jakewharton.rxbinding3.view.clicks
import com.unicorn.tickets.R
import com.unicorn.tickets.app.Configs
import com.unicorn.tickets.app.Global
import com.unicorn.tickets.app.safeClicks
import com.unicorn.tickets.ui.act.checkTicket.BaseScanTicketCodeAct
import kotlinx.android.synthetic.main.act_scan_ticket_code.*
import org.joda.time.DateTime

class ScanTicketCodeAct : BaseScanTicketCodeAct() {

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
        llCheckin.safeClicks().subscribe {
            isCheckin = true
            scanTicketCode()
        }
    }

    override val layoutId = R.layout.act_scan_ticket_code

}