package com.unicorn.tickets.ui.act.stateStatistic

import androidx.recyclerview.widget.LinearLayoutManager
import com.unicorn.tickets.R
import com.unicorn.tickets.data.model.Stat
import com.unicorn.tickets.ui.adapter.StatAdapter
import com.unicorn.tickets.ui.base.BaseAct
import com.unicorn.tickets.ui.other.StatFooter
import com.unicorn.tickets.ui.other.StatHeader
import kotlinx.android.synthetic.main.ui_title_recycler.*

class StateStatisticAct : BaseAct() {

    override val layoutId = R.layout.ui_title_recycler

    private val statAdapter = StatAdapter()

    override fun initViews() {
        titleBar.setTitle("报表统计")
        with(recyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = statAdapter
        }
    }

    override fun bindIntent() {
        statAdapter.addHeaderView(StatHeader(context = this))
        val data = listOf(
            Stat("标准票", 20, 1200),
            Stat("老人票", 20, 600),
            Stat("学生票", 20, 600),
            Stat("车票", 20, 200),
            Stat("特价票", 20, 600),
            Stat("60元9折", 20, 1080),
            Stat("60元8折", 20, 960)
        )
        statAdapter.setNewData(data)
        statAdapter.addFooterView(StatFooter(context = this))
    }

}