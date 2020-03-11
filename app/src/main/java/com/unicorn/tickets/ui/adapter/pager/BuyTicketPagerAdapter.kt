package com.unicorn.tickets.ui.adapter.pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.unicorn.tickets.app.Global
import com.unicorn.tickets.ui.fra.EmptyFra
import com.unicorn.tickets.ui.fra.GroupProductFra
import com.unicorn.tickets.ui.fra.ProductFra
import org.joda.time.DateTime

class BuyTicketPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    companion object {
        val titles get() = listOf("${DateTime().toString("yyyy-MM-dd")} 总库存：${Global.inventory}")
    }

    override fun getItem(position: Int): Fragment = ProductFra()

    override fun getCount() = titles.size

}