package com.unicorn.tickets.ui.base

import com.unicorn.tickets.app.helper.DialogHelper
import com.unicorn.tickets.app.helper.ExceptionHelper
import com.unicorn.tickets.app.helper.PrintHelper
import com.unicorn.tickets.app.observeOnMain
import com.unicorn.tickets.data.model.TakeTicketParam
import io.reactivex.rxkotlin.subscribeBy

abstract class PrintAct : BaseAct() {

    override fun bindIntent() {
        PrintHelper.bindService(this)
    }

    override fun onDestroy() {
        PrintHelper.unBindService(this)
        super.onDestroy()
    }

    protected fun takeTicket(orderId: Long) {
        val mask = DialogHelper.showMask(this)
        api.takeTicket(TakeTicketParam(orderId))
            .observeOnMain(this)
            .subscribeBy(
                onSuccess = {
                    mask.dismiss()
                    if (it.failed) return@subscribeBy
                    // todo delete
                    if (it.data.isEmpty()) {
//                        "订单0张票".toast()
                        return@subscribeBy
                    }
                    DialogHelper.showPrintingDialog(this, it.data.size)
                    PrintHelper.printTickets(this, it.data)
                },
                onError = {
                    mask.dismiss()
                    ExceptionHelper.showPrompt(it)
                }
            )
    }

}