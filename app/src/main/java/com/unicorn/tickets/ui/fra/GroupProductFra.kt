package com.unicorn.tickets.ui.fra

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ToastUtils
import com.unicorn.tickets.R
import com.unicorn.tickets.app.*
import com.unicorn.tickets.app.helper.DialogHelper.showMask
import com.unicorn.tickets.app.helper.ExceptionHelper
import com.unicorn.tickets.data.event.PrintGroupApplyEvent
import com.unicorn.tickets.data.event.ProductQuantityChange
import com.unicorn.tickets.data.model.CreateGroupOrderParam
import com.unicorn.tickets.data.model.PayOrderParam
import com.unicorn.tickets.data.model.PayOrderResponse
import com.unicorn.tickets.data.model.PayType
import com.unicorn.tickets.ui.act.buyTicket.PayFailedAct
import com.unicorn.tickets.ui.act.buyTicket.PaySuccessAct
import com.unicorn.tickets.ui.adapter.pager.GroupProductAdapter
import com.unicorn.tickets.ui.base.BaseFra
import io.reactivex.functions.Consumer
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fra_group_product.*
import java.net.SocketTimeoutException
import java.text.DecimalFormat

class GroupProductFra : BaseFra() {

    override fun initViews() {
        fun initRecyclerView() {
            recyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = groupProductAdapter
                addDefaultItemDecoration(1)
            }
        }

        initRecyclerView()
    }

    override fun bindIntent() {
        fun getGroupProductList() {
            val mask = showMask(context!!)
            api.getGroupProductList()
                .observeOnMain(this)
                .subscribeBy(
                    onSuccess = {
                        mask.dismiss()
                        if (it.failed) return@subscribeBy
                        groupProductAdapter.setNewData(it.data)
                    },
                    onError = {
                        mask.dismiss()
                        ExceptionHelper.showPrompt(it)
                    }
                )
        }

//        fun createOrder() {
//            val mask = showMask(context!!)
//            api.createOrder(createOrderParam)
//                .observeOnMain(this)
//                .subscribeBy(
//                    onSuccess = { response ->
//                        mask.dismiss()
//                        if (response.failed) return@subscribeBy
//                        orderId = response.data.orderId
//                        showPayTypeDialog(context!!, createOrderParam.totalPrice)
//                        // clear
//                        with(productAdapter) {
//                            data.forEach { it.quantity = 0 }
//                            notifyDataSetChanged()
//                        }
//                    },
//                    onError = {
//                        mask.dismiss()
//                        ExceptionHelper.showPrompt(it)
//                    }
//                )
//        }

        fun createOrderX() {
           if (createGroupOrderParam.stypeNum == 0) {
                ToastUtils.showShort("至少选择一种票")
               return
           }
            RxBus.post(PrintGroupApplyEvent(groupProductAdapter.data))
        }

        rtvSubmitOrder.safeClicks().subscribe { createOrderX() }
        getGroupProductList()
    }

    override fun registerEvent() {
        fun scan() {
//            IntentIntegrator.forSupportFragment(this)
//                .setPrompt("扫码支付")
//                .initiateScan()
        }

        RxBus.registerEvent(this, ProductQuantityChange::class.java, Consumer {
            val i = DecimalFormat("#.##").format(createGroupOrderParam.totalPrice)
            tvDescription.text = "总计金额：${i}元"
        })

//        RxBus.registerEvent(this, PayType::class.java, Consumer {
//            when (it) {
//                PayType.QRCode -> scan()
//                else -> payOrder(it)
//            }
//        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
//        if (result.contents == null) {
//            ToastUtils.showShort("已取消")
//        } else {
//            val authCode = result.contents
//            payOrder(PayType.QRCode, authCode)
//        }
    }

    private fun payOrder(payType: PayType, authCode: String = "0") {
        fun paySuccess(payOrderResponse: PayOrderResponse) {
            Intent(context, PaySuccessAct::class.java).apply {
                putExtra(Key.PayOrderResponse, payOrderResponse)
            }.let { startActivity(it) }
        }

        fun payFailed() {
            startActivity(Intent(context, PayFailedAct::class.java))
        }

        val i = PayOrderParam(orderId = orderId, payType = payType.type, authCode = authCode)
        val mask = showMask(context!!)
        api.payOrder(i)
            .observeOnMain(this)
            .subscribeBy(
                onSuccess = {
                    mask.dismiss()
                    if (it.failed) payFailed()
                    else paySuccess(it.data)
                },
                onError = {
                    mask.dismiss()
                    if (it is SocketTimeoutException) {
                        ToastUtils.showShort("支付超时")
                        payFailed()
                        return@subscribeBy
                    }
                    ExceptionHelper.getPrompt(it).toast()
                }
            )
    }

    private var orderId: Long = 0

    private val createGroupOrderParam get() = CreateGroupOrderParam(groupProductAdapter.data.filter { it.quantity > 0 })

    private val groupProductAdapter = GroupProductAdapter()

    override val layoutId = R.layout.fra_group_product


}