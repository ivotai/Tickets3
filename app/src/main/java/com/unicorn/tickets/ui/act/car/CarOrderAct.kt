package com.unicorn.tickets.ui.act.car

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
import com.unicorn.tickets.app.helper.UpdateHelper
import com.unicorn.tickets.data.model.CarOrder
import com.unicorn.tickets.data.model.RefundCarParam
import com.unicorn.tickets.ui.act.car.CarTicketScanAct.Companion.quantity
import com.unicorn.tickets.ui.adapter.CarOrderAdapter
import com.unicorn.tickets.ui.base.KVHolder
import com.unicorn.tickets.ui.base.SimplePageAct
import io.reactivex.functions.Consumer
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.act_car_order.*
import kotlinx.android.synthetic.main.act_car_order.titleBar
import kotlinx.android.synthetic.main.act_login.*
import org.joda.time.DateTime

class CarOrderAct : SimplePageAct<CarOrder, KVHolder>() {

    private var travelDate = DateTime()

    override fun initViews() {
        super.initViews()
        titleBar.setTitle("车票收款列表")
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
                    this@CarOrderAct.tvTravelDate.text =
                        travelDate.toString(Configs.displayDateFormat)
                    getCarOrderStatus()
                    loadFirstPage()
                }
            }
        }
        getCarOrderStatus()

    }

    private fun getCarOrderStatus() {
        val red400 = ContextCompat.getColor(this, R.color.md_red_400)
        api.getCarOrderStats(travelDate = travelDate.toString(Configs.displayDateFormat3))
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
        RxBus.registerEvent(this, RefundCarParam::class.java, Consumer { refundCarParam ->
            MaterialDialog(this).show {
                title(text = "提示")
                message(text = "是否要退款？")
                positiveButton {
                    refundTicket(refundCarParam = refundCarParam)
                }
            }
        })
    }

    private fun refundTicket(refundCarParam: RefundCarParam) {
        val mask = DialogHelper.showMask(this)
        api.refundTicket(refundCarParam)
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

    override val simpleAdapter: BaseQuickAdapter<CarOrder, KVHolder> = CarOrderAdapter()

    override fun loadPage(page: Int) =
        api.getOrderList(page = page, travelDate = travelDate.toString(Configs.displayDateFormat3))

    override val layoutId = R.layout.act_car_order

    override val mRecyclerView: RecyclerView
        get() = recyclerView

    override val mSwipeRefreshLayout: SwipeRefreshLayout
        get() = swipeRefreshLayout

}