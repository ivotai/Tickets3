package com.unicorn.tickets.ui.act.checkTicket

import androidx.core.content.ContextCompat
import cn.iwgang.simplifyspan.SimplifySpanBuild
import cn.iwgang.simplifyspan.unit.SpecialTextUnit
import com.unicorn.tickets.R
import com.unicorn.tickets.app.Configs
import com.unicorn.tickets.app.Key
import com.unicorn.tickets.app.helper.AppHelper
import com.unicorn.tickets.app.safeClicks
import com.unicorn.tickets.data.model.CvTicketResponse
import com.unicorn.tickets.data.model.SourceType
import kotlinx.android.synthetic.main.act_validate_ticket_success.*
import org.joda.time.DateTime

class ValidateTicketSuccessAct : CvTicketAct() {

    override val layoutId = R.layout.act_validate_ticket_success

    override fun initViews() {
        titleBar.setTitle("验票成功")

        val red400 = ContextCompat.getColor(this, R.color.md_red_400)

//        with(checkinTicketResponse) {
//            tvTicketType.text = SimplifySpanBuild(productName)
//                .append(SpecialTextUnit(" $quantity ", red400))
//                .append("人")
//                .build()
//        }
        tvSourceTypeText.text = "购票渠道：${SourceType.sourceTypeMap[checkinTicketResponse.sourceType]}"
//        tvTicketId.text = "购票单号：${checkinTicketResponse.ticketId}"


        constraintLayout.background = AppHelper.getDashBackground(this)

//        checkinTicketResponse.checkinLog?.let {
//            if (it.isNotEmpty()) with(it[0]) {
//                tvCheckinType.text = "检票方式：${if (checkin_type == 1) "在线" else "离线"}"
//                tvCheckinTime.text =
//                    "检票时间：${DateTime(checkin_time).toString(Configs.displayDateFormat2)}"
//                tvAndroidId.text = "检票设备：$gate_tag"
//                tvChecker.text = "检票人：$checker"
//            }
//        }
    }

    override fun bindIntent() {
        super.bindIntent()
        llCv.safeClicks().subscribe { scanTicketCode() }
    }

    private val checkinTicketResponse by lazy { intent.getSerializableExtra(Key.CvTicketResponse) as CvTicketResponse }

}