package com.unicorn.tickets.ui.adapter.pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.unicorn.tickets.ui.fra.EmptyFra
import com.unicorn.tickets.ui.fra.GroupProductFra
import com.unicorn.tickets.ui.fra.ProductFra

class BuyTicketPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    companion object {
        val titles = listOf("标准", "其他")
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> ProductFra()
//            1 -> GroupProductFra()
            else -> EmptyFra()
        }
    }

    override fun getCount() = titles.size

}