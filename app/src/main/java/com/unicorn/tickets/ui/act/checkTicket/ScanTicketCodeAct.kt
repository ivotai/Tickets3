package com.unicorn.tickets.ui.act.checkTicket

import android.content.Intent
import com.blankj.utilcode.util.ToastUtils
import com.unicorn.tickets.app.Key
import com.unicorn.tickets.app.helper.DialogHelper
import com.unicorn.tickets.app.helper.ExceptionHelper
import com.unicorn.tickets.app.observeOnMain
import com.unicorn.tickets.data.model.CheckinTicketParam
import com.unicorn.tickets.ui.act.main.SunmiScannerHelper
import com.unicorn.tickets.ui.base.BaseAct
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.rxkotlin.subscribeBy

abstract class ScanTicketCodeAct : BaseAct() {

    override fun bindIntent() {
        sunmiScannerHelper = SunmiScannerHelper(this, object : SunmiScannerHelper.ScanListener {
            override fun onScanResult(result: String) {
                onTicketCodeGet(result)
            }
        })
    }

    protected fun scanTicketCode() {
        if (sunmiScannerHelper.scannerModel in listOf(103, 106, 107)) {
            DialogHelper.showScaningDialog(this)
            sunmiScannerHelper.scan()
        } else
            startSunmiQrcodeScanner()
    }

    private val qrcodeRequestCode = 1

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
                onTicketCodeGet(value)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun onTicketCodeGet(ticketCode: String) {
        checkinTicket(ticketCode)
    }

    private fun checkinTicket(ticketCode: String) {
        val mask = DialogHelper.showMask(this)
        checkApi.checkinTicket(CheckinTicketParam(ticketCode = ticketCode))
            .observeOnMain(this)
            .subscribeBy(
                onSuccess = { response ->
                    mask.dismiss()
                    val success = response.data.returnCode == "00"
                    if (success) {
                        Intent(this, CheckinTicketSuccessAct::class.java).apply {
                            putExtra(Key.Param, response.data)
                        }.let { startActivity(it) }
                    } else {
                        val failReason = response.data.message
                        Intent(this, CheckinTicketFailedAct::class.java).apply {
                            putExtra(Key.PayOrderResponse, failReason)
                        }.let { startActivity(it) }
                    }
                },
                onError = {
                    mask.dismiss()
                    if (it is UndeliverableException) return@subscribeBy
                    ExceptionHelper.showPrompt(it)
                }
            )
    }

    private lateinit var sunmiScannerHelper: SunmiScannerHelper

    override fun onDestroy() {
        sunmiScannerHelper.release()
        super.onDestroy()
    }


}