package com.unicorn.tickets.ui.adapter.pager

import com.chad.library.adapter.base.BaseQuickAdapter
import com.jakewharton.rxbinding3.widget.textChanges
import com.unicorn.tickets.R
import com.unicorn.tickets.data.model.base.GroupProduct
import com.unicorn.tickets.ui.base.KVHolder
import kotlinx.android.synthetic.main.item_group_product.*
import kotlinx.android.synthetic.main.number_picker.view.*

class GroupProductAdapter : BaseQuickAdapter<GroupProduct, KVHolder>(R.layout.item_group_product) {

    override fun convert(helper: KVHolder, item: GroupProduct) {
        helper.apply {
            tvTitle.text = item.name
            tvPrice.text = "￥${item.defaultPrice}"
            numberPicker.setValue(item.quantity)
            numberPicker.tvNumber
                .textChanges()
                .subscribe {
                    item.quantity = it.toString().toInt()
                }
        }
    }

}