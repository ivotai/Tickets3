package com.unicorn.tickets.ui.act.parking

import android.graphics.Color
import androidx.constraintlayout.widget.ConstraintLayout
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.DeviceUtils
import com.jakewharton.rxbinding3.view.clicks
import com.unicorn.tickets.R
import com.unicorn.tickets.app.Configs
import com.unicorn.tickets.app.Global
import com.unicorn.tickets.app.safeClicks
import com.unicorn.tickets.app.startAct
import com.unicorn.tickets.ui.base.BaseAct
import kotlinx.android.synthetic.main.act_parking_main.*
import org.joda.time.DateTime

class ParkingMainAct : BaseAct() {

    override fun initViews() {
        titleBar.setTitle("扫码停车",false)
        titleBar.findViewById<ConstraintLayout>(R.id.root)
            .setBackgroundColor(Color.parseColor("#F6B750"))
        tvVersionName.text = "程序版本：${AppUtils.getAppVersionName()}"
        tvUsername.text = "登录用户：${Global.loginResponse.user.username}"
        tvLoginTime.text = "登录时间：${DateTime().toString(Configs.displayDateFormat2)}"
        tvAndroidId.text = "设备 ID：${DeviceUtils.getAndroidID()}"
    }

    override fun bindIntent() {
        super.bindIntent()
        ivSignOut.clicks().mergeWith(tvSignOut.clicks()).subscribe { finish() }
//        llCarSearch.safeClicks().subscribe {
//            startAct(CarOrderAct::class.java)
//        }
        llScan.safeClicks().subscribe {
            startAct(ParkingScanAct::class.java)
        }
    }

    override val layoutId = R.layout.act_parking_main

}