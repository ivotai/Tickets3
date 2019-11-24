package com.unicorn.tickets.ui.fra

import androidx.core.content.ContextCompat
import cn.iwgang.simplifyspan.SimplifySpanBuild
import cn.iwgang.simplifyspan.unit.SpecialTextUnit
import com.unicorn.tickets.R
import com.unicorn.tickets.app.RxBus
import com.unicorn.tickets.app.safeClicks
import com.unicorn.tickets.data.event.CurrentItem
import com.unicorn.tickets.data.event.PaySuccessEvent
import com.unicorn.tickets.ui.act.car.CarTicketScanAct
import com.unicorn.tickets.ui.act.car.CarTicketScanAct.Companion.quantity
import com.unicorn.tickets.ui.base.BaseFra
import kotlinx.android.synthetic.main.act_checkin_ticket_success.*
import kotlinx.android.synthetic.main.fra_confirm_pay.*
import kotlinx.android.synthetic.main.fra_confirm_pay.tvPrompt

class ConfirmPayFra : BaseFra() {

    override val layoutId = R.layout.fra_confirm_pay

    override fun bindIntent() {
        rtvContinue.safeClicks().subscribe {
            RxBus.post(PaySuccessEvent())
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        if (isVisibleToUser) {
            tvPrompt.text ="已成功收款 $quantity 人"
        }
    }

}
