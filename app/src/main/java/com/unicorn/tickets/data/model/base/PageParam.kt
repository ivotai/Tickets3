package com.unicorn.tickets.data.model.base

data class PageParam(
    val page: Int,
    val pageSize: Int = 5,
    val keyword: String = ""
)