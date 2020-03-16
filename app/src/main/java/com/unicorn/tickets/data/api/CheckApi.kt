package com.unicorn.tickets.data.api

import com.unicorn.tickets.data.model.CheckinTicketParam
import com.unicorn.tickets.data.model.CvTicketResponse
import com.unicorn.tickets.data.model.ValidateTicketParam
import com.unicorn.tickets.data.model.base.BaseResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface CheckApi {

    @POST("checkin/checkin")
    fun checkinTicket(@Body checkinTicketParam: CheckinTicketParam): Single<BaseResponse<CvTicketResponse>>

    @POST("checkin/validate")
    fun validateTicket(@Body validateTicketParam: ValidateTicketParam): Single<BaseResponse<CvTicketResponse>>

}