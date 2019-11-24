package com.unicorn.tickets.ui.adapter.pager

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.unicorn.tickets.app.Key
import com.unicorn.tickets.ui.fra.TeamFra

class TeamPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    companion object {
        val titles = listOf("未登记", "已登记")
    }

    override fun getItem(position: Int): Fragment {
        val groupApplyStatus = if (position == 0) 1 else 3
        val teamFra = TeamFra()
        val bundle = Bundle()
        bundle.putInt(Key.GroupApplyStatus, groupApplyStatus)
        teamFra.arguments = bundle
        return teamFra
    }

    override fun getCount() = titles.size

}