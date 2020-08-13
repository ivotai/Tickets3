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
    val sourceTypeName: String?,
    val statusName: String,
    val studentCount: Int,
    var ticketCode: String,
    val checkinQuantity: Int,
    val useQuantity: Int,
    val photoUrl: String,
    val groupOrderInfo: GroupOrderInfo
) : Serializable {

    val isT get() = ticketCode.startsWith("T")

    val isG get() = ticketCode.startsWith("G")

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

data class GroupOrderInfo(
    val createTime: Any,
    val detailList: List<Any>,
    val groupName: String,
    val guideAmount: Any,
    val guideType: Any,
    val leader: String,
    val leaderMobile: String,
    val memo: String,
    val orderId: Long,
    val payAmount: Any,
    val status: Int,
    val statusText: Any,
    val ticketStatus: Int,
    val totalAmount: Any,
    val totalPeopleCount: Int,
    val travelDate: Long
) : Serializable