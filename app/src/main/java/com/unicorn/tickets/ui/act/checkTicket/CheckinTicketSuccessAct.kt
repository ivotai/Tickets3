package com.unicorn.tickets.ui.act.checkTicket

import android.media.MediaPlayer
import androidx.core.content.ContextCompat
import cn.iwgang.simplifyspan.SimplifySpanBuild
import cn.iwgang.simplifyspan.unit.SpecialTextUnit
import com.blankj.utilcode.util.DeviceUtils
import com.blankj.utilcode.util.ToastUtils
import com.github.florent37.rxsharedpreferences.RxSharedPreferences
import com.unicorn.tickets.R
import com.unicorn.tickets.app.Configs
import com.unicorn.tickets.app.Key
import com.unicorn.tickets.app.RxBus
import com.unicorn.tickets.app.helper.AppHelper
import com.unicorn.tickets.app.safeClicks
import com.unicorn.tickets.data.event.ScanTicketCodeEvent
import com.unicorn.tickets.data.model.CvTicketResponse
import com.unicorn.tickets.ui.base.BaseAct
import kotlinx.android.synthetic.main.act_checkin_ticket_success.*
import kotlinx.android.synthetic.main.act_checkin_ticket_success.constraintLayout
import kotlinx.android.synthetic.main.act_checkin_ticket_success.titleBar
import kotlinx.android.synthetic.main.act_checkin_ticket_success.tvAndroidId
import kotlinx.android.synthetic.main.act_checkin_ticket_success.tvPrompt
import kotlinx.android.synthetic.main.act_checkin_ticket_success.tvSourceTypeText
import kotlinx.android.synthetic.main.act_checkin_ticket_success.tvTicketType
import org.joda.time.DateTime
import java.lang.Exception

class CheckinTicketSuccessAct : BaseAct() {

    override fun initViews() {
        titleBar.setTitle("检票成功")
        val red400 = ContextCompat.getColor(this, R.color.md_red_400)

        with(checkinTicketResponse) {
            tvTicketType.text = SimplifySpanBuild(productName)
                .append(SpecialTextUnit(" $peopleCount ", red400))
                .append("人")
                .build()
            tvPrompt.text = "本日检票${checkinCount + 1}人"
            tvDate.text =
                "有效日期：${DateTime(beginTravelDate).toString("yyyy-MM-dd")} 至 ${DateTime(endTravelDate).toString(
                    "yyyy-MM-dd"
                )}"
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
        try {
            playMedia()
        } catch (e: Exception) {
            ToastUtils.showShort("播放提示音错误")
        }
        llContinueScanTicketCode.safeClicks().subscribe {
            finish()
            RxBus.post(ScanTicketCodeEvent())
        }
    }

    private val mediaPlayer = MediaPlayer()

    private fun playMedia() {
        val peopleCount = checkinTicketResponse.peopleCount
        val fileName = if (peopleCount in 1..9) "suc_0$peopleCount.mp3" else "suc_01.mp3"
        val assetFileDescriptor = assets.openFd(fileName)
        with(mediaPlayer) {
            reset()
            setDataSource(
                assetFileDescriptor.fileDescriptor,
                assetFileDescriptor.startOffset,
                assetFileDescriptor.length
            )
            prepare()
            start()
        }
    }

    private val checkinTicketResponse by lazy { intent.getSerializableExtra(Key.Param) as CvTicketResponse }

    override val layoutId = R.layout.act_checkin_ticket_success

}