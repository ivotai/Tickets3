package com.unicorn.tickets.ui.act.car

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
import com.unicorn.tickets.ui.act.checkTicket.CvTicketAct
import kotlinx.android.synthetic.main.act_battery_car.*
import kotlinx.android.synthetic.main.act_cv_menu.ivSignOut
import kotlinx.android.synthetic.main.act_cv_menu.titleBar
import kotlinx.android.synthetic.main.act_cv_menu.tvAndroidId
import kotlinx.android.synthetic.main.act_cv_menu.tvLoginTime
import kotlinx.android.synthetic.main.act_cv_menu.tvSignOut
import kotlinx.android.synthetic.main.act_cv_menu.tvUsername
import kotlinx.android.synthetic.main.act_cv_menu.tvVersionName
import org.joda.time.DateTime

class BatteryCarAct : CvTicketAct() {

    override val shouldFinish = false

    override val layoutId = R.layout.act_battery_car

    override fun initViews() {
        titleBar.setTitle("扫码检票",false)
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
        llCarSearch.safeClicks().subscribe {
            startAct(CarOrderAct::class.java)
        }
        llScan.safeClicks().subscribe {
            startAct(CarTicketScanAct::class.java)
        }
    }

}