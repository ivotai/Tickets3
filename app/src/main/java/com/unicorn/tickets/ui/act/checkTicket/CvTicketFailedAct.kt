package com.unicorn.tickets.ui.act.checkTicket

import android.graphics.Color
import com.blankj.utilcode.util.DeviceUtils
import com.github.florent37.rxsharedpreferences.RxSharedPreferences
import com.unicorn.tickets.R
import com.unicorn.tickets.app.Configs
import com.unicorn.tickets.app.Key
import com.unicorn.tickets.app.helper.AppHelper
import com.unicorn.tickets.app.safeClicks
import kotlinx.android.synthetic.main.act_cv_ticket_failed.*
import org.joda.time.DateTime

class CvTicketFailedAct : CvTicketAct() {

    override fun initViews() {
        titleBar.setTitle("${cv}失败")
        tvFailReason.text = failReason
        tvTime.text = "${cv}时间：${DateTime().toString(Configs.displayDateFormat2)}"
        tvAndroidId.text = "${cv}设备：${DeviceUtils.getAndroidID()}"
        RxSharedPreferences.with(this).getString(Key.Username, "").subscribe {
            tvUsername.text = "${cv}人：$it"
        }
        tvCv.text = "继续$cv"
        llCv.setBackgroundColor(Color.parseColor(if(isCheckin) "#4D92E0" else "#F5A52C"))
        constraintLayout.background = AppHelper.getDashBackground(this)
    }

    override fun bindIntent() {
        super.bindIntent()
        llCv.safeClicks().subscribe { scanTicketCode() }
    }

    private val failReason by lazy { intent.getStringExtra(Key.FailReason) }

    override val layoutId = R.layout.act_cv_ticket_failed

}