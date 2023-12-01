package com.unicorn.tickets.data.api

import com.unicorn.tickets.data.model.BoatTicketResponse
import com.unicorn.tickets.data.model.base.BaseResponse
import io.reactivex.Single
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query


interface V3Api {

    @POST("appointment/admin/activity/billing")
    fun checkBoatTicketCode(@Query("code") code: String ): Single<BaseResponse<BoatTicketResponse>>

    @PUT("appointment/admin/activity/write_off")
    fun checkPlaceTicketCode(@Query("code") code:String): Single<BaseResponse<String>>

}