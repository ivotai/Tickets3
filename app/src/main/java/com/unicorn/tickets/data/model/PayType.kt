package com.unicorn.tickets.data.model

import androidx.annotation.DrawableRes
import com.unicorn.tickets.R

enum class PayType(val type: Int, val text: String, @DrawableRes val resId: Int) {
    QRCode(1, "扫码付", R.mipmap.icon_pay_qrcode),
    Cash(2, "现金付", R.mipmap.icon_pay_cash),
    Bank(3, "刷卡付", R.mipmap.icon_pay_bank)
}
