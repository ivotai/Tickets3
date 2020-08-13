package com.unicorn.tickets.data.model

import java.io.Serializable

data class CvTicketResponse(
    val adultCount: Int,
    val beginTravelDate: Long,
    val buyPrice: Any,
    val checkinCount: Int,
    val childCount: Int,
    val endTravelDate: Long,
    val freeOldCount: Int,
    val issueTime: Any,
    val message: String,
    val oldCount: Int,
    val peopleCount: Int,
    val productName: String,
    val returnCode: String,
    val sourceType: Int,
    val sourceTypeName: String,
    val statusName: String,
    val studentCount: Int,
    var ticketCode: String,
    val checkinQuantity: Int,
    val userQuantity: Int,
    val photoUrl: String
) : Serializable {

    val isT get() = ticketCode.startsWith("T")

}

data class CheckinLog(
    val checker: String,
    val checkin_time: Long,
    val checkin_type: Int,
    val gate_tag: String
) : Serializable

data class D(
    val adultCount: Int,
    val beginTravelDate: Long,
    val buyPrice: Any,
    val checkinCount: Int,
    val childCount: Int,
    val endTravelDate: Long,
    val freeOldCount: Int,
    val issueTime: Any,
    val message: String,
    val oldCount: Int,
    val peopleCount: Int,
    val productName: String,
    val returnCode: String,
    val sourceType: Int,
    val sourceTypeName: String,
    val statusName: String,
    val studentCount: Int
)