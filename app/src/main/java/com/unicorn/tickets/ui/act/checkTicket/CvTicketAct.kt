package com.unicorn.tickets.ui.act.checkTicket

import android.content.Intent
import android.media.MediaPlayer
import com.blankj.utilcode.util.ToastUtils
import com.unicorn.tickets.app.Key
import com.unicorn.tickets.app.helper.DialogHelper
import com.unicorn.tickets.app.helper.ExceptionHelper
import com.unicorn.tickets.app.helper.NetworkHelper
import com.unicorn.tickets.app.observeOnMain
import com.unicorn.tickets.data.model.CheckinTicketParam
import com.unicorn.tickets.data.model.CvTicketResponse
import com.unicorn.tickets.data.model.ValidateTicketParam
import com.unicorn.tickets.ui.act.main.SunmiScannerHelper
import com.unicorn.tickets.ui.base.BaseAct
import io.reactivex.rxkotlin.subscribeBy

abstract class CvTicketAct : BaseAct() {

    protected open val shouldFinish: Boolean = true

    companion object {
        var isCheckin = true
        val cv: String
            get() {
                return if (isCheckin) "检票" else "验票"
            }
    }

    private lateinit var sunmiScannerHelper: SunmiScannerHelper

    override fun bindIntent() {
        sunmiScannerHelper = SunmiScannerHelper(this, object : SunmiScannerHelper.ScanListener {
            override fun onScanResult(result: String) {
                cv(result)
            }
        })
    }

    override fun onDestroy() {
        sunmiScannerHelper.release()
        super.onDestroy()
    }

    protected fun scanTicketCode() {
        if (sunmiScannerHelper.scannerModel in listOf(103, 106, 107)) {
            DialogHelper.showCvingDialog(this, cv)
            sunmiScannerHelper.scan()
        } else
            startSunmiQrcodeScanner()
    }

    private fun startSunmiQrcodeScanner() {
        val intent = Intent("com.summi.scan")
        intent.setPackage("com.sunmi.sunmiqrcodescanner")
        startActivityForResult(intent, qrcodeRequestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == qrcodeRequestCode) {
            if (data == null) {
                ToastUtils.showShort("已取消")
            } else {
                val bundle = data.extras
                val result = bundle!!.getSerializable("data") as ArrayList<*>
                val hashMap = result[0] as HashMap<*, *>
                val value = hashMap["VALUE"] as String
                cv(value)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun cv(ticketCode: String) {
        if (isCheckin) c(ticketCode)
        else v(ticketCode)
    }

    private fun v(ticketCode: String) {
        val mask = DialogHelper.showMask(this)
        // 检票用的独立服务器，需要切换 baseUrl
//        NetworkHelper.switchBaseUrl()
        checkApi.validateTicket(ValidateTicketParam(ticketCode = ticketCode))
            .observeOnMain(this)
            .subscribeBy(
                onSuccess = {
//                    NetworkHelper.switchBaseUrl()
                    mask.dismiss()
                    if (it.success) validateTicketSuccess(it.data)
                    // 现在失败理由暂时放在 message 里
                    else checkinTicketFailed(it.message)
                    finishIfNeed()
                },
                onError = {
//                    NetworkHelper.switchBaseUrl()
                    mask.dismiss()
                    ExceptionHelper.showPrompt(it)
                }
            )
    }

    private val mediaPlayer = MediaPlayer()

    private fun playMedia(success: Boolean, peopleCount: Int) {
        var fileName = if (peopleCount in 1..9) "suc_0$peopleCount.mp3" else "err.mp3"
        if (!success) fileName = "err.mp3"
        val assetFileDescriptor = assets.openFd(fileName)
        with(mediaPlayer) {
            reset()
            setDataSource(
                assetFileDescriptor.fileDescriptor,
                assetFileDescriptor.startOffset,
                assetFileDescriptor.length
            )
//            setOnPreparedListener { it.start() }
//            prepareAsync()
            prepare()
            start()
        }
    }

    private fun c(ticketCode: String) {
        val mask = DialogHelper.showMask(this)
//        NetworkHelper.switchBaseUrl()
        checkApi.checkinTicket(CheckinTicketParam(ticketCode = ticketCode))
            .observeOnMain(this)
            .subscribeBy(
                onSuccess = {
//                    NetworkHelper.switchBaseUrl()
                    mask.dismiss()
                    if (it.data.returnCode == "00") checkinTicketSuccess(it.data)
                    else checkinTicketFailed(it.data.message)
                    playMedia(it.data.returnCode == "00", it.data.peopleCount)
                    finishIfNeed()
                },
                onError = {
//                    NetworkHelper.switchBaseUrl()
                    mask.dismiss()
                    ExceptionHelper.showPrompt(it)
                }
            )
    }

    private fun checkinTicketSuccess(cvTicketResponse: CvTicketResponse) {
        Intent(this, CheckinTicketSuccessAct::class.java).apply {
            putExtra(Key.CvTicketResponse, cvTicketResponse)
        }.let { startActivity(it) }
    }

    private fun validateTicketSuccess(cvTicketResponse: CvTicketResponse) {
        Intent(this, ValidateTicketSuccessAct::class.java).apply {
            putExtra(Key.CvTicketResponse, cvTicketResponse)
        }.let { startActivity(it) }
    }

    private fun checkinTicketFailed(failReason: String) {
        Intent(this, CvTicketFailedAct::class.java).apply {
            putExtra(Key.FailReason, failReason)
        }.let { startActivity(it) }
    }

    private fun finishIfNeed() {
        if (shouldFinish) finish()
    }

    private val qrcodeRequestCode = 1

}