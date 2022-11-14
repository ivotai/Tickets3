package com.unicorn.tickets.ui.act

import android.media.MediaPlayer
import com.blankj.utilcode.util.DeviceUtils
import com.blankj.utilcode.util.ToastUtils
import com.github.florent37.rxsharedpreferences.RxSharedPreferences
import com.unicorn.tickets.R
import com.unicorn.tickets.app.*
import com.unicorn.tickets.app.helper.AppHelper
import com.unicorn.tickets.data.event.ScanTicketCodeEvent
import com.unicorn.tickets.ui.base.BaseAct
import kotlinx.android.synthetic.main.act_checkin_ticket_success.*
import org.joda.time.DateTime

class CheckinBoatTicketSuccessAct : BaseAct() {

    override fun initViews() {
        titleBar.setTitle("检票成功")
        if (Global.roleTag == Place) {
            titleBar.setTitle("核销成功")
        }

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
        val fileName = "suc_01.mp3"
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

//    private val boatTicketResponse by lazy { intent.getStringExtra(Key.Param)  as BoatTicketResponse}

    override val layoutId = R.layout.act_checkin_boat_ticket_success

}