package com.unicorn.tickets.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.jakewharton.rxbinding3.widget.textChanges
import com.unicorn.tickets.R
import com.unicorn.tickets.data.model.Product
import com.unicorn.tickets.ui.base.KVHolder
import kotlinx.android.synthetic.main.item_product.*
import kotlinx.android.synthetic.main.number_picker.view.*

class ProductAdapter : BaseQuickAdapter<Product, KVHolder>(R.layout.item_product) {

    override fun convert(helper: KVHolder, item: Product) {
        helper.apply {
            tvTitle.text = item.title
            tvPrice.text = "￥${item.price}"
            numberPicker.setValue(item.quantity)
            numberPicker.tvNumber
                .textChanges()
                .subscribe {
                    item.quantity = it.toString().toInt()
                }
        }
    }

}