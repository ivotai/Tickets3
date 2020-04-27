package com.unicorn.tickets.ui.fra.parking

import androidx.core.content.ContextCompat
import cn.iwgang.simplifyspan.SimplifySpanBuild
import cn.iwgang.simplifyspan.unit.SpecialTextUnit
import com.unicorn.tickets.R
import com.unicorn.tickets.app.RxBus
import com.unicorn.tickets.app.helper.DialogHelper
import com.unicorn.tickets.app.helper.ExceptionHelper
import com.unicorn.tickets.app.observeOnMain
import com.unicorn.tickets.app.safeClicks
import com.unicorn.tickets.data.event.CurrentItem
import com.unicorn.tickets.data.model.PayParkingOrderParam
import com.unicorn.tickets.ui.act.main.SunmiScannerHelper
import com.unicorn.tickets.ui.act.parking.ParkingScanAct.Companion.quantity
import com.unicorn.tickets.ui.base.BaseFra
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fra_parking_scan.*

class ParkingScanFra : BaseFra() {

    private var _req = ""

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        if (isVisibleToUser) {
            _req = java.util.UUID.randomUUID().toString()
            val red400 = ContextCompat.getColor(context!!, com.unicorn.tickets.R.color.md_red_400)
            tvPrompt.text = SimplifySpanBuild("正在支付")
                .append(SpecialTextUnit(" $quantity ", red400))
                .append("辆")
                .build()
            sunmiScannerHelper.scan()
        }
    }

    override fun bindIntent() {
        sunmiScannerHelper =
            SunmiScannerHelper(context!!, object : SunmiScannerHelper.ScanListener {
                override fun onScanResult(result: String) {
                    pay(result)
                }
            })
        tvBack.safeClicks().subscribe {
            RxBus.post(CurrentItem(0))
        }
    }

    private fun pay(result: String) {
        val mask = DialogHelper.showMask(context!!)
        api.payParkingOrder(
            _req = _req,
            payParkingOrderParam = PayParkingOrderParam(authCode = result, quantity = quantity)
        )
            .observeOnMain(this)
            .subscribeBy(
                onSuccess = {
                    mask.dismiss()
                    if (it.failed) return@subscribeBy
                    RxBus.post(CurrentItem(2))
                },
                onError = {
                    mask.dismiss()
                    ExceptionHelper.showPrompt(it)
                }
            )
    }

    private lateinit var sunmiScannerHelper: SunmiScannerHelper

    override fun onDestroy() {
        sunmiScannerHelper.release()
        super.onDestroy()
    }

    override val layoutId = R.layout.fra_parking_scan

}