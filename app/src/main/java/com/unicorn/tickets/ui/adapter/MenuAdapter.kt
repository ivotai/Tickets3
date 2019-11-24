package com.unicorn.tickets.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.unicorn.tickets.R
import com.unicorn.tickets.app.RxBus
import com.unicorn.tickets.app.safeClicks
import com.unicorn.tickets.app.startAct
import com.unicorn.tickets.app.toast
import com.unicorn.tickets.data.event.ScanTicketCodeEvent
import com.unicorn.tickets.data.model.Menu
import com.unicorn.tickets.data.model.Menu.*
import com.unicorn.tickets.ui.act.buyTicket.BuyTicketAct
import com.unicorn.tickets.ui.act.checkTicket.CvTicketAct
import com.unicorn.tickets.ui.act.orderSearch.OrderSearchAct
import com.unicorn.tickets.ui.act.stateStatistic.StateStatisticAct
import com.unicorn.tickets.ui.act.team.TeamAct
import com.unicorn.tickets.ui.base.KVHolder
import kotlinx.android.synthetic.main.item_menu.*

class MenuAdapter : BaseQuickAdapter<Menu, KVHolder>(R.layout.item_menu) {

    override fun convert(helper: KVHolder, item: Menu) {
        helper.apply {
            tvText.text = item.text
            ivIcon.setImageResource(item.iconRes)

            root.safeClicks().subscribe {
                when (item) {
                    BuyTicket ->
                        context.startAct(BuyTicketAct::class.java)
                    GetTicket, OrderSearch, ErrorOrder, RefundTicket ->
                        context.startAct(OrderSearchAct::class.java)
                    Team ->
                        context.startAct(TeamAct::class.java)
                    StateStatistic ->
                        context.startAct(StateStatisticAct::class.java)
                    SystemSetting ->
                        "尚未开发".toast()
                    CheckinTicket -> {
                        CvTicketAct.isCheckin = true
                        RxBus.post(ScanTicketCodeEvent())
                    }
                    ValidateTicket -> {
                        CvTicketAct.isCheckin = false
                        RxBus.post(ScanTicketCodeEvent())
                    }
                }
            }
        }
    }

}