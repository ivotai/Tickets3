package com.unicorn.tickets.data.model

import com.unicorn.tickets.data.model.base.GroupProduct

class CreateGroupOrderParam(
    private val detailList: List<GroupProduct>,
    val category: Int = 1,  // 1表示标准票
    val totalPrice: Double = detailList.sumByDouble { it.defaultPrice * it.quantity },
    val stypeNum: Int = detailList.size  // 选了几种票
)

