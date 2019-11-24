package com.unicorn.tickets.data.model

class CreateOrderParam(

    private val detailList: List<Product>,
    val category: Int = 1,  // 1表示标准票
    val totalPrice: Double = detailList.sumByDouble { it.price * it.quantity },
    val sourceType: Int = 12,// pda
    val stypeNum: Int = detailList.size  // 选了几种票
)

