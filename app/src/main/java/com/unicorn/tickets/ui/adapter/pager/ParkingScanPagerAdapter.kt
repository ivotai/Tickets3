package com.unicorn.tickets.ui.adapter.pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.unicorn.tickets.ui.fra.CarQuantityFra
import com.unicorn.tickets.ui.fra.CarScaningFra
import com.unicorn.tickets.ui.fra.ConfirmPayFra
import com.unicorn.tickets.ui.fra.parking.ParkingConfirmFra
import com.unicorn.tickets.ui.fra.parking.ParkingQuantityFra
import com.unicorn.tickets.ui.fra.parking.ParkingScanFra

class ParkingScanPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    companion object {
        val titles = listOf("选择停车数量", "扫码支付", "完成支付")
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> ParkingQuantityFra()
            1 -> ParkingScanFra()
            else -> ParkingConfirmFra()
        }
    }

    override fun getCount() = titles.size

}