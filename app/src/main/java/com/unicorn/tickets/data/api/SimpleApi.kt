package com.unicorn.tickets.data.api

import com.blankj.utilcode.util.DeviceUtils
import com.unicorn.tickets.app.Configs
import com.unicorn.tickets.app.Global
import com.unicorn.tickets.data.model.*
import com.unicorn.tickets.data.model.base.*
import io.reactivex.Observable
import io.reactivex.Single
import org.joda.time.DateTime
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SimpleApi {

    @GET("login/account")
    fun login(
        @Query("username") username: String,
        @Query("password") password: String
    ): Single<LoginResponse>

    @GET("login/silence")
    fun loginSilently(@Query("token") token: String = Global.token): Call<LoginResponse>

    @GET("api/v1/pda/ticket/product/list")
    fun getProductList(): Single<BaseResponse<List<Product>>>

    @POST("api/v1/pda/ticket/order/create")
    fun createOrder(@Body createOrderParam: CreateOrderParam): Single<BaseResponse<CreateOrderResponse>>

    @POST("api/v1/pda/ticket/order/pay")
    fun payOrder(@Body payOrderParam: PayOrderParam): Single<BaseResponse<PayOrderResponse>>

    @POST("api/v1/pda/ticket/order/pay/cancel")
    fun cancelPay(@Body cancelPayParam: CancelPayParam): Observable<BaseResponse<CancelPayResponse>>

    @GET("api/v1/pda/ticket/order/getTicketOrderInfo")
    fun getTicketOrderInfo(@Query("orderId") orderId: Long): Single<Order>

    @POST("api/v1/pda/ticket/take")
    fun takeTicket(@Body takeTicketParam: TakeTicketParam): Single<BaseResponse<List<Ticket>>>

    @GET("api/v1/pda/ticket/order/list")
    fun getOrderList(
        @Query("beginDate") beginDate: String = DateTime().toString("yyyy/MM/dd"),
        @Query("endDate") endDate: String = DateTime().toString("yyyy/MM/dd"),
        @Query("page") page: Int,
        @Query("keyword") keyword: String,
        @Query("pageSize") pageSize: Int = Configs.defaultPageSize
    ): Single<BaseResponse<PageResponse<Order>>>


//    @POST("checkin/checkin")
//    fun checkinTicket(@Body checkinTicketParam: CheckinTicketParam): Single<BaseResponse<CvTicketResponse>>
//
//    @POST("checkin/validate")
//    fun validateTicket(@Body validateTicketParam: ValidateTicketParam): Single<BaseResponse<CvTicketResponse>>


    @POST("api/v1/pda/ticket/order/refund")
    fun refundTicket(@Body refundTicketParam: RefundTicketParam): Single<BaseResponse<RefundTicketResponse>>

    // group
    @GET("api/v1/pda/ticket/order/groupApply/list")
    fun getOrderGroupApplyList(
        @Query("keyword") keyword: String,
        @Query("status") status: Int,   // 1 未登记 3 已登记
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int = Configs.defaultPageSize
    ): Single<BaseResponse<PageResponse<GroupApply>>>

    @GET("api/v1/pda/ticket/order/group/listGroupProduct")
    fun getGroupProductList(): Single<BaseResponse<List<GroupProduct>>>

    @GET("api/v1/pda/ticket/order/group/listWaitPay")
    fun listWaitPay(): Observable<Any>

    // version
    @GET("api/v1/pda/version/1/check")
    fun checkVersion(): Single<CheckVersionResponse>

    @POST("api/v1/pda/batteryCar/order/pay")
    fun payOrder(
        @Query("_req") _req: String,
        @Body payCarOrderParam: PayCarOrderParam
    ): Single<BaseResponse<PayCarOrderResponse>>

    @GET("api/v1/pda/batteryCar/order/list")
    fun getOrderList(
        @Query("beginDate") beginDate: String = DateTime().toString("yyyy/MM/dd"),
        @Query("endDate") endDate: String = DateTime().toString("yyyy/MM/dd"),
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int = Configs.defaultPageSize,
        @Query("conductorId") conductorId: Long = Global.loginResponse.user.id,
        @Query("terminalEquipmentTag") terminalEquipmentTag: String = DeviceUtils.getAndroidID(),
        @Query("travelDate") travelDate: String
    ): Single<BaseResponse<PageResponse<CarOrder>>>

    @GET("api/v1/pda/batteryCar/order/stats")
    fun getCarOrderStats(
        @Query("conductorId") conductorId: Long = Global.loginResponse.user.id,
        @Query("terminalEquipmentTag") terminalEquipmentTag: String = DeviceUtils.getAndroidID(),
        @Query("travelDate") travelDate: String
    ): Single<BaseResponse<List<CarStat>>>

    @POST("api/v1/pda/batteryCar/order/refund")
    fun refundTicket(@Body refundCarParam: RefundCarParam): Single<BaseResponse<Any>>

    @GET("api/v1/pda/ticket/getInventory")
    fun getInventory(
        @Query("travelDate") beginDate: String = DateTime().toString("yyyy-MM-dd")
    ): Single<Int>

    @POST("api/v1/pda/parking/order/pay")
    fun payParkingOrder(
        @Query("_req") _req: String,
        @Body parkingOrderParam: PayParkingOrderParam
    ): Single<BaseResponse<PayParkingOrderResponse>>

    @GET("api/v1/pda/parking/order/list")
    fun getParkingOrderList(
        @Query("beginDate") beginDate: String = DateTime().toString("yyyy/MM/dd"),
        @Query("endDate") endDate: String = DateTime().toString("yyyy/MM/dd"),
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int = Configs.defaultPageSize,
        @Query("conductorId") conductorId: Long = Global.loginResponse.user.id,
        @Query("terminalEquipmentTag") terminalEquipmentTag: String = DeviceUtils.getAndroidID(),
        @Query("travelDate") travelDate: String
    ): Single<BaseResponse<PageResponse<ParkingOrder>>>

    @GET("api/v1/pda/parking/order/stats")
    fun getParkingOrderStats(
        @Query("conductorId") conductorId: Long = Global.loginResponse.user.id,
        @Query("terminalEquipmentTag") terminalEquipmentTag: String = DeviceUtils.getAndroidID(),
        @Query("travelDate") travelDate: String
    ): Single<BaseResponse<List<ParkingOrderStat>>>

    @POST("api/v1/pda/parking/order/refund")
    fun refundParkingOrder(@Body refundParkingOrderParam: RefundParkingOrderParam): Single<BaseResponse<Any>>

}