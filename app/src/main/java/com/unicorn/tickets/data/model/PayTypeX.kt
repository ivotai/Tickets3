package com.unicorn.tickets.data.model

data class PayTypeX(
    val payType: PayType,
    var isChecked: Boolean = false
) {

    companion object {
        fun data(): List<PayTypeX> {
            return listOf(
                PayTypeX(PayType.QRCode, true),
                PayTypeX(PayType.Cash),
                PayTypeX(PayType.Bank)
            )
        }
    }

}