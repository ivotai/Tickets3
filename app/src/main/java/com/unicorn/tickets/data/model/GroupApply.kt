package com.unicorn.tickets.data.model

import java.io.Serializable

data class GroupApply(
    val approveTime: Long,
    val approverId: String,
    val approverName: String,
    val attachmentList: List<Any>,
    val contact: String,
    val customerId: String,
    val customerName: String,
    val groupName: String,
    val groupProperty: String,
    val mobile: String,
    val notes: String,
    val objectId: String,
    val owner: String,
    val peopleCount: Int,
    val travelDate: Long
): Serializable