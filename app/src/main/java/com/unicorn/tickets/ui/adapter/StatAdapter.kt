package com.unicorn.tickets.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.unicorn.tickets.R
import com.unicorn.tickets.data.model.Stat
import com.unicorn.tickets.ui.base.KVHolder
import kotlinx.android.synthetic.main.item_stat.*

class StatAdapter : BaseQuickAdapter<Stat, KVHolder>(R.layout.item_stat) {

    override fun convert(helper: KVHolder, item: Stat) {
        helper.apply {
            tvType.text = item.type
            tvQuantity.text = item.quantity.toString()
            tvAmount.text = item.amount.toString()
        }
    }

}