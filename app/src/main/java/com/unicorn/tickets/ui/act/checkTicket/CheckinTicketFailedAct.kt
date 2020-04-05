package com.unicorn.tickets.ui.act.checkTicket

import android.graphics.Color
import android.media.MediaPlayer
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
import com.unicorn.tickets.ui.base.BaseAct
import kotlinx.android.synthetic.main.act_checkin_ticket_failed.*
import org.joda.time.DateTime
import java.lang.Exception

class CheckinTicketFailedAct : BaseAct() {

    override fun initViews() {
        titleBar.setTitle("检票失败")
        tvFailReason.text = failReason
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
        val fileName = "err.mp3"
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

    private val failReason by lazy { intent.getStringExtra(Key.Param) }

    override val layoutId = R.layout.act_checkin_ticket_failed

}