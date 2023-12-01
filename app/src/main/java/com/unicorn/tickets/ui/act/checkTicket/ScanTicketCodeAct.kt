package com.unicorn.tickets.ui.act.checkTicket

import android.content.Intent
import android.util.Log
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ToastUtils
import com.google.gson.Gson
import com.sunmi.eidlibrary.EidConstants
import com.sunmi.eidlibrary.EidSDK
import com.sunmi.eidlibrary.bean.ResultInfo
import com.unicorn.tickets.app.*
import com.unicorn.tickets.app.di.ComponentHolder
import com.unicorn.tickets.app.helper.DialogHelper
import com.unicorn.tickets.app.helper.ExceptionHelper
import com.unicorn.tickets.data.model.CheckinTicketParam
import com.unicorn.tickets.data.model.SpecificCheckinParam
import com.unicorn.tickets.ui.act.CheckinBoatTicketSuccessAct
import com.unicorn.tickets.ui.act.main.SunmiScannerHelper
import com.unicorn.tickets.ui.base.BaseAct
import io.reactivex.rxkotlin.subscribeBy
import okhttp3.internal.wait
import java.util.ArrayList
import java.util.HashMap

abstract class ScanTicketCodeAct : BaseAct() {

    override fun bindIntent() {
        //Step 1 初始化SDK 传入AppId
        EidSDK.init(applicationContext, Configs.appId) { code, msg ->
            when (code) {
                EidConstants.EID_INIT_SUCCESS -> {
//                    ToastUtils.showShort("成功")
                }
                else -> {
                    ToastUtils.showShort("初始化失败：$msg")
                }
            }
        }

        sunmiScannerHelper = SunmiScannerHelper(this, object : SunmiScannerHelper.ScanListener {
            override fun onScanResult(result: String) {
                // 假如是下一个界面传来的扫码结果，关闭下一个界面
                val top = ActivityUtils.getTopActivity()
                if (this@ScanTicketCodeAct != top) top.finish()
                onTicketCodeGet(result)
            }
        })
    }

    protected fun scanTicketCode() {
        if (sunmiScannerHelper.scannerModel in listOf(103, 106, 107)) {
            DialogHelper.showScaningDialog(this)
            sunmiScannerHelper.scan()
        } else startSunmiQrcodeScanner()
    }

    private val qrcodeRequestCode = 1

    private fun startSunmiQrcodeScanner() {
        val intent = Intent("com.summi.scan")
        intent.setPackage("com.sunmi.sunmiqrcodescanner")
        startActivityForResult(intent, qrcodeRequestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == qrcodeRequestCode) {
            if (data == null) {
                ToastUtils.showShort("已取消")
            } else {
                val bundle = data.extras
                val result = bundle!!.getSerializable("data") as ArrayList<*>
                val hashMap = result[0] as HashMap<*, *>
                val value = hashMap["VALUE"] as String
                onTicketCodeGet(value)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun onTicketCodeGet(ticketCode: String) {
        when (Global.roleTag) {
            Boat -> {
                checkinBoatTicketCode(ticketCode)
            }
            Place -> {
                checkinPlaceTicketCode(ticketCode)
            }
            else -> {
                checkinTicket(ticketCode)
            }
        }
    }

    private fun checkinBoatTicketCode(code: String) {
        val mask = DialogHelper.showMask(this)
        v3Api.checkBoatTicketCode(code = code).observeOnMain(this)
            .subscribeBy(onSuccess = { response ->
                mask.dismiss()
                if (response.success) {
                    Intent(this, CheckinBoatTicketSuccessAct::class.java).apply {
//                        putExtra(Key.Param, response.data)
                    }.let { startActivity(it) }
                } else {
                    val failReason = response.message
                    Intent(this, CheckinTicketFailedAct::class.java).apply {
                        putExtra(Key.Param, failReason)
                    }.let { startActivity(it) }
                }
            }, onError = {
                mask.dismiss()
                ExceptionHelper.showPrompt(it)
            })
    }

    private fun checkinPlaceTicketCode(code: String) {
        val mask = DialogHelper.showMask(this)
        v3Api.checkPlaceTicketCode(code = code).observeOnMain(this)
            .subscribeBy(onSuccess = { response ->
                mask.dismiss()
                if (response.success) {
                    // todo to be Place success act
                    Intent(this, CheckinBoatTicketSuccessAct::class.java).apply {
//                        putExtra(Key.Param, response.data)
                    }.let { startActivity(it) }
                } else {
                    val failReason = response.message
                    Intent(this, CheckinTicketFailedAct::class.java).apply {
                        putExtra(Key.Param, failReason)
                    }.let { startActivity(it) }
                }
            }, onError = {
                mask.dismiss()
                ExceptionHelper.showPrompt(it)
            })
    }


    private fun checkinTicket(ticketCode: String) {
        val mask = DialogHelper.showMask(this)
        checkApi.checkinTicket(CheckinTicketParam(ticketCode = ticketCode)).observeOnMain(this)
            .subscribeBy(onSuccess = { response ->
                mask.dismiss()
                response.data.ticketCode = ticketCode
                val success = response.data.returnCode == "00"
                if (success) {
                    Intent(this, CheckinTicketSuccessAct::class.java).apply {
                        putExtra(Key.Param, response.data)
                    }.let { startActivity(it) }
                } else {
                    val failReason = response.data.message
                    Intent(this, CheckinTicketFailedAct::class.java).apply {
                        putExtra(Key.Param, failReason)
                    }.let { startActivity(it) }
                }
            }, onError = {
                mask.dismiss()
                ExceptionHelper.showPrompt(it)
            })
    }

    private lateinit var sunmiScannerHelper: SunmiScannerHelper

    override fun onDestroy() {
        sunmiScannerHelper.release()
        EidSDK.destroy()
        super.onDestroy( )
    }

    protected fun startCheckCard() {
        //Step 2 开启读卡 -> 调用startCheckCard方法，通过回调结果处理业务逻辑
        //注：默认循环读卡，只会回调一次EidConstants.READ_CARD_READY
        //Step 2 开启读卡 -> 调用startCheckCard方法，通过回调结果处理业务逻辑
        //注：默认循环读卡，只会回调一次EidConstants.READ_CARD_READY

        val map: MutableMap<String, Any> = HashMap()
        map[EidSDK.PARAMS_IS_READ_PICTURE] = false
        EidSDK.startCheckCard(this, { code, msg ->
            when (code) {
                EidConstants.ERR_NFC_NOT_SUPPORT -> ToastUtils.showShort("机器不支持NFC")

                EidConstants.ERR_NETWORK_NOT_CONNECTED -> ToastUtils.showShort("网络未连接，连接网络")


                EidConstants.ERR_NFC_CLOSED -> "NFC 未打开".toast()

                EidConstants.READ_CARD_READY -> {
                    //Step 3 读卡准备完成 -> 业务方可以引导用户开始进行刷卡操作
                    ToastUtils.showShort("请刷卡，刷卡时请勿移动卡片")
                }

                EidConstants.READ_CARD_START -> {
                    //Step 4 读卡中 -> 业务方可以提醒用户"读卡中，请勿移动卡片"
                    ToastUtils.showShort("开始读卡，请勿移动")
                }

                EidConstants.READ_CARD_SUCCESS -> {
                    //Step 5 读卡成功 -> 返回的msg为reqId，通过 reqId 业务方走云对云方案获取身份证信息
                    //注：如不需要循环读卡，可在此处调用stopCheckCard方法
//                    ToastUtils.showShort("读卡成功 $msg")
                    EidSDK.stopCheckCard(this)
                    getIDCardInfo(msg)
//                    Log.e(TAG, "读卡成功，reqId：$msg")
                }

                EidConstants.READ_CARD_FAILED -> {
                    //*** 异常处理： 读卡失败，请重新读卡 ***
                    ToastUtils.showShort("读卡失败 $msg")
                }

                else -> {
                    //*** 异常处理： 其他失败 - code为错误码，msg为详细错误原因 需要重新调用 startCheckCard 方法 （手动触发，非自动）***
                    ToastUtils.showShort( "读卡失败：code:$code,msg:$msg")
                }
            }
        }, map)
    }

    private fun getIDCardInfo(reqId:String){
        //调用SDK的解码，存在泄漏key的风险，建议使用云对云方案
//传入读卡获取的reqId，商米partner平台上的appkey，以及结果callback
        //调用SDK的解码，存在泄漏key的风险，建议使用云对云方案
//传入读卡获取的reqId，商米partner平台上的appkey，以及结果callback
        EidSDK.getIDCardInfo(reqId, Configs.appKey) { code, data ->
            //EidConstants.DECODE_SUCCESS -> 解码成功，data为身份证信息的gson格式，可直接解析成SDK中提供的 ResultInfo 实体类
            if (code == EidConstants.DECODE_SUCCESS) {
//                ToastUtils.showShort("身份证解析成功")
                val result = Gson().fromJson(
                    data,
                    ResultInfo::class.java
                )
                val idnum = result.info.idnum
                ComponentHolder.appComponent.api().specificCheckin(SpecificCheckinParam(identityCardNo = idnum)).observeOnMain(this)
                    .subscribeBy(onSuccess = {
                       if (it.result == 1){
                           ToastUtils.showLong("可以入园")
                       }else{
                           ToastUtils.showLong(it.message)
                       }
                    }, onError = {
                        ExceptionHelper.showPrompt(it)
                    })
            } else {
                //解码失败，code 为错误吗，data为错误原因
                ToastUtils.showLong("身份证解析失败 $code $data")
            }
        }
    }


}