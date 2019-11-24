package com.unicorn.tickets.ui.act.checkTicket

import androidx.core.content.ContextCompat
import cn.iwgang.simplifyspan.SimplifySpanBuild
import cn.iwgang.simplifyspan.unit.SpecialTextUnit
import com.blankj.utilcode.util.DeviceUtils
import com.github.florent37.rxsharedpreferences.RxSharedPreferences
import com.unicorn.tickets.R
import com.unicorn.tickets.app.Configs
import com.unicorn.tickets.app.Key
import com.unicorn.tickets.app.helper.AppHelper
import com.unicorn.tickets.app.safeClicks
import com.unicorn.tickets.data.model.CvTicketResponse
import com.unicorn.tickets.data.model.SourceType
import kotlinx.android.synthetic.main.act_checkin_ticket_success.*
import kotlinx.android.synthetic.main.act_checkin_ticket_success.constraintLayout
import kotlinx.android.synthetic.main.act_checkin_ticket_success.llCv
import kotlinx.android.synthetic.main.act_checkin_ticket_success.titleBar
import kotlinx.android.synthetic.main.act_checkin_ticket_success.tvAndroidId
import kotlinx.android.synthetic.main.act_checkin_ticket_success.tvPrompt
import kotlinx.android.synthetic.main.act_checkin_ticket_success.tvSourceTypeText
import kotlinx.android.synthetic.main.act_checkin_ticket_success.tvTicketType
import kotlinx.android.synthetic.main.act_validate_ticket_success.*
import org.joda.time.DateTime

class CheckinTicketSuccessAct : CvTicketAct() {

    override val layoutId = R.layout.act_checkin_ticket_success

    override fun initViews() {
        titleBar.setTitle("检票成功")
        val red400 = ContextCompat.getColor(this, R.color.md_red_400)

        with(checkinTicketResponse) {
            tvTicketType.text = SimplifySpanBuild(productName)
                .append(SpecialTextUnit(" $peopleCount ", red400))
                .append("人")
                .build()
            tvPrompt.text = "本日检票${checkinCount + 1}人"
            tvDate.text = "有效日期：${DateTime(beginTravelDate).toString("yyyy-MM-dd")} 至 ${DateTime(endTravelDate).toString("yyyy-MM-dd")}"
        }


        tvSourceTypeText.text = "购票渠道：${checkinTicketResponse.sourceTypeName}"

        tvTime.text = "检票时间：${DateTime().toString(Configs.displayDateFormat2)}"
        tvAndroidId.text = "检票设备：${DeviceUtils.getAndroidID()}"
        RxSharedPreferences.with(this).getString(Key.Username, "").subscribe {
            tvUsername.text = "检票人：$it"
        }
        constraintLayout.background = AppHelper.getDashBackground(this)
    }

    override fun bindIntent() {
        super.bindIntent()
        llCv.safeClicks().subscribe { scanTicketCode() }
    }

    private val checkinTicketResponse by lazy { intent.getSerializableExtra(Key.CvTicketResponse) as CvTicketResponse }

}