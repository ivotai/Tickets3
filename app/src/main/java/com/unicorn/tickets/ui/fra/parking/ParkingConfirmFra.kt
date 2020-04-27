package com.unicorn.tickets.ui.fra.parking

import com.unicorn.tickets.R
import com.unicorn.tickets.app.RxBus
import com.unicorn.tickets.app.safeClicks
import com.unicorn.tickets.data.event.PaySuccessEvent
import com.unicorn.tickets.ui.act.parking.ParkingScanAct.Companion.quantity
import com.unicorn.tickets.ui.base.BaseFra
import kotlinx.android.synthetic.main.fra_parking_confirm.*

class ParkingConfirmFra : BaseFra() {

    override fun bindIntent() {
        rtvContinue.safeClicks().subscribe {
            RxBus.post(PaySuccessEvent())
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        if (isVisibleToUser) {
            tvPrompt.text ="已成功收款 $quantity 辆"
        }
    }

    override val layoutId = R.layout.fra_parking_confirm

}
