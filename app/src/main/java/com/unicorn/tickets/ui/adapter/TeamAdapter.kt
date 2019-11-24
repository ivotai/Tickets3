package com.unicorn.tickets.ui.adapter

import android.content.Intent
import com.chad.library.adapter.base.BaseQuickAdapter
import com.unicorn.tickets.R
import com.unicorn.tickets.app.Key
import com.unicorn.tickets.app.safeClicks
import com.unicorn.tickets.app.startAct
import com.unicorn.tickets.data.model.GroupApply
import com.unicorn.tickets.ui.act.team.TeamDetailAct
import com.unicorn.tickets.ui.base.KVHolder
import kotlinx.android.synthetic.main.item_team.*

class TeamAdapter : BaseQuickAdapter<GroupApply, KVHolder>(R.layout.item_team) {

    override fun convert(helper: KVHolder, item: GroupApply) {
        helper.apply {
            tvOrderId.text = "订单号：${item.objectId}"
            tvTitle.text = "${item.groupName} 预计${item.peopleCount}人"
            tvDescription.text = "${item.contact} ${item.mobile}"
            root.safeClicks().subscribe {
                Intent(context,TeamDetailAct::class.java).apply {
                    putExtra(Key.GroupApply,item)
                }.let { context.startActivity(it) }
            }
        }
    }

}