package com.unicorn.tickets.data.model

data class CheckVersionResponse(
    val description: String,
    val lastUpdateTime: String,
    val url: String,
    val version: String
)