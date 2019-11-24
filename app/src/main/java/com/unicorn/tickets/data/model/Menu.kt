package com.unicorn.tickets.data.model

import androidx.annotation.DrawableRes
import com.unicorn.tickets.R
import com.unicorn.tickets.app.Global

enum class Menu(val text: String, @DrawableRes val iconRes: Int) {
    BuyTicket("售票", R.mipmap.menu_buy_ticket),
    GetTicket("扫码取票", R.mipmap.menu_get_ticket),
    RefundTicket("退票", R.mipmap.menu_refund_ticket),
    Team("团队登记", R.mipmap.menu_team),
    OrderSearch("订单查询", R.mipmap.menu_order_search),
    ErrorOrder("异常订单", R.mipmap.menu_error_order),
    StateStatistic("报表统计", R.mipmap.menu_state_statistic),
    CheckinTicket("检票", R.mipmap.menu_inspect_ticket),
    SystemSetting("系统设置", R.mipmap.menu_setting),
    CarScan("车票扫码", R.mipmap.menu_bus_ticket),
    ShipScan("船票扫码", R.mipmap.menu_boat_ticket),
    ValidateTicket("验票", R.mipmap.menu_error_order);

    companion object {
        fun all(): List<Menu> {
            val roleTag = Global.loginResponse.user.roleTag
            return listOf(
                    BuyTicket,
                    Team,
                    OrderSearch
                )
//            else
//                listOf(
//                    CheckinTicket,
//                    ValidateTicket
//                )
        }
    }

}