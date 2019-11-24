package com.unicorn.tickets.data.model.base

data class PageResponse<T>(
    val content: List<T>,
    val totalElements: String
)


