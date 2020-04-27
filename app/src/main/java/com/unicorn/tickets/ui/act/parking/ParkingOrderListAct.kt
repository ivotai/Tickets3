package com.unicorn.tickets.ui.act.parking

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import cn.iwgang.simplifyspan.SimplifySpanBuild
import cn.iwgang.simplifyspan.unit.SpecialTextUnit
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.datetime.datePicker
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.unicorn.tickets.R
import com.unicorn.tickets.app.*
import com.unicorn.tickets.app.helper.DialogHelper
import com.unicorn.tickets.app.helper.ExceptionHelper
import com.unicorn.tickets.data.model.ParkingOrder
import com.unicorn.tickets.data.model.RefundCarParam
import com.unicorn.tickets.data.model.RefundParkingOrderParam
import com.unicorn.tickets.ui.adapter.ParkingOrderAdapter
import com.unicorn.tickets.ui.base.KVHolder
import com.unicorn.tickets.ui.base.SimplePageAct
import io.reactivex.functions.Consumer
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.act_parking_order_list.*
import org.joda.time.DateTime

class ParkingOrderListAct : SimplePageAct<ParkingOrder, KVHolder>() {

    private var travelDate = DateTime()

    override fun initViews() {
        super.initViews()
        titleBar.setTitle("停车收款列表")
        titleBar.findViewById<ConstraintLayout>(R.id.root)
            .setBackgroundColor(Color.parseColor("#F6B750"))
        val grey300 = ContextCompat.getColor(this, R.color.md_grey_400)
        tvTravelDate.background = GradientDrawable().apply {
            setStroke(ConvertUtils.dp2px(1f), grey300)
        }
        tvTravelDate.text = travelDate.toString(Configs.displayDateFormat)
    }

    override fun bindIntent() {
        super.bindIntent()
        tvTravelDate.safeClicks().subscribe {
            MaterialDialog(this).show {
                datePicker { _, date ->
                    travelDate = DateTime(date)
                    this@ParkingOrderListAct.tvTravelDate.text =
                        travelDate.toString(Configs.displayDateFormat)
                    getOrderStatus()
                    loadFirstPage()
                }
            }
        }
        getOrderStatus()
    }

    private fun getOrderStatus() {
        val red400 = ContextCompat.getColor(this, R.color.md_red_400)
        api.getParkingOrderStats(travelDate = travelDate.toString(Configs.displayDateFormat3))
            .observeOnMain(this)
            .subscribeBy(
                onSuccess = {
                    if (it.failed) return@subscribeBy
                    val build = SimplifySpanBuild("")
                    it.data.forEach { carStat ->
                        build.append(" ${carStat.payTypeText}:")
                            .append(SpecialTextUnit(" ${carStat.total_amount} ", red400))
                            .append("元 ")
                    }
                    tvSum.text = build.build()
                },
                onError = {
                    ExceptionHelper.showPrompt(it)
                }
            )
    }

    override fun registerEvent() {
        RxBus.registerEvent(this, RefundParkingOrderParam::class.java, Consumer { refundCarParam ->
            MaterialDialog(this).show {
                title(text = "提示")
                message(text = "是否要退款？")
                positiveButton {
                    refund( refundCarParam)
                }
            }
        })
    }

    private fun refund(refundParkingOrderParam: RefundParkingOrderParam) {
        val mask = DialogHelper.showMask(this)
        api.refundParkingOrder(refundParkingOrderParam)
            .observeOnMain(this)
            .subscribeBy(
                onSuccess = {
                    mask.dismiss()
                    if (it.failed) return@subscribeBy
                    ToastUtils.showShort("退款成功")
                    loadFirstPage()
                },
                onError = {
                    mask.dismiss()
                    ExceptionHelper.showPrompt(it)
                }
            )
    }

    override val simpleAdapter: BaseQuickAdapter<ParkingOrder, KVHolder> = ParkingOrderAdapter()

    override fun loadPage(page: Int) =
        api.getParkingOrderList(page = page, travelDate = travelDate.toString(Configs.displayDateFormat3))

    override val layoutId = R.layout.act_parking_order_list

    override val mRecyclerView: RecyclerView
        get() = recyclerView

    override val mSwipeRefreshLayout: SwipeRefreshLayout
        get() = swipeRefreshLayout

}