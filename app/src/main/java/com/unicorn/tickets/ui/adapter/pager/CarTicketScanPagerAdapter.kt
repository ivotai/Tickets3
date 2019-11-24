package com.unicorn.tickets.ui.adapter.pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.unicorn.tickets.ui.fra.CarQuantityFra
import com.unicorn.tickets.ui.fra.CarScaningFra
import com.unicorn.tickets.ui.fra.ConfirmPayFra

class CarTicketScanPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    companion object {
        val titles = listOf("选择车票数量", "扫码支付", "完成支付")
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> CarQuantityFra()
            1 -> CarScaningFra()
            else -> ConfirmPayFra()
        }
    }

    override fun getCount() = titles.size

}