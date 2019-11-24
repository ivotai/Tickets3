package com.unicorn.tickets.ui.act.team

import com.unicorn.tickets.R
import com.unicorn.tickets.app.Configs
import com.unicorn.tickets.app.Key
import com.unicorn.tickets.data.model.GroupApply
import com.unicorn.tickets.ui.base.BaseAct
import kotlinx.android.synthetic.main.act_order_detail.titleBar
import kotlinx.android.synthetic.main.act_team_detail.*
import org.joda.time.DateTime

class TeamDetailAct : BaseAct() {

    override fun initViews() {
        titleBar.setTitle("团队登记")

        with(groupApply) {
            tvGroupName.text = "团队名称：$groupName"
            tvTravelDate.text = DateTime(travelDate).toString(Configs.displayDateFormat)
            tvGroupProperty.text = groupProperty
            tvPeopleCount.text = peopleCount.toString()
            tvNotes.text = notes

            //
            tvContact.text = contact
            tvMobile.text = mobile

            // todo delete
//            Global.groupApply = this
        }
    }

    override fun bindIntent() {
//        rtvBuyTicket.safeClicks().subscribe {
//           Intent(this, BuyTicketAct::class.java).apply {
//                putExtra(Key.BuyTicketIndex, 1)
//            }.let { startActivity(it) }
//        }
    }

    override val layoutId = R.layout.act_team_detail

    private val groupApply by lazy { intent.getSerializableExtra(Key.GroupApply) as GroupApply }

}