package com.unicorn.tickets.data.model

import java.io.Serializable

data class BoatTicketResponse(
    val id: Int, val recordId: Int, val beginTime: Long, val name: String, val phone: String
) : Serializable

