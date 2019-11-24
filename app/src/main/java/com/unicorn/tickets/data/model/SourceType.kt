package com.unicorn.tickets.data.model

object SourceType {

    val sourceTypeMap = HashMap<Int,String>()

    init {
        sourceTypeMap[11] = "窗口售票"
        sourceTypeMap[12] = "PDA售票"
        sourceTypeMap[13] = "自助售票机"
        sourceTypeMap[14] = "官网"
        sourceTypeMap[21] = "微信小程序"
        sourceTypeMap[22] = "支付宝小程序"
        sourceTypeMap[31] = "美团"
        sourceTypeMap[32] = "携程"
        sourceTypeMap[33] = "驴妈妈"
        sourceTypeMap[34] = "同程旅游"
        sourceTypeMap[35] = "懒虎"
        sourceTypeMap[36] = "其他渠道"
    }

    // PDA售票
    val PDA_SALES = 12

    // 微信小程序
    val WX_APP = 21

    // 支付宝小程序
    val ALIPAY_APP = 22

    // 美团
    val OTA_MEITUAN = 31

    // 携程
    val OTA_CTRIP = 32

    // 驴妈妈
    val OTA_LVMM = 33
}
