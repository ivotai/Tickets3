package com.unicorn.tickets.app

import com.blankj.utilcode.util.ConvertUtils

object Configs {

    const val defaultPageSize = 10

    val defaultPaddingDp = ConvertUtils.dp2px(16f)

//    const val baseUrl = "https://kjgk.natapp4.cc/ticket/"

//    const val checkinBaseUrl = "http://117.131.92.118:9015"

    // 正式
    const val baseUrl = "https://ts.csnbgsh.com/"

    const val boatBaseUrl = "https://cstest.naturewharf.com/"


//   测试
//    const val baseUrl = "http://cs.lefz.kjgk.xyz/"

//    const val baseUrl = "https://tstest.csnbgsh.com/"

//    const val checkinBaseUrl = "http://192.168.239.103/"

    const val remoteCheckinBaseUrl = "https://tc.csnbgsh.com:9016/"

    const val localCheckinBaseUrl = "http://192.168.239.101:8080/"

    const val keyCheckinBaseUrl = "keyCheckinBaseUrl"

    const val displayDateFormat = "yyyy-MM-dd"

    const val displayDateFormat2 = "yyyy-MM-dd HH:mm:ss"

    const val displayDateFormat3 = "yyyy/MM/dd"

}