package com.unicorn.tickets.ui.other

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.unicorn.tickets.app.RxBus
import com.unicorn.tickets.app.safeClicks
import com.unicorn.tickets.data.model.PayTypeX
import com.unicorn.tickets.ui.adapter.PayTypeAdapter
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.dialog_pay_type.view.*


class PayTypeDialogView(context: Context, totalPrice: Double) : FrameLayout(context),
    LayoutContainer {

    private val payTypeAdapter = PayTypeAdapter()

    lateinit var dialog: MaterialDialog

    init {
        LayoutInflater.from(context)
            .inflate(com.unicorn.tickets.R.layout.dialog_pay_type, this, true)

        tvTotalPrice.text = "￥$totalPrice"
        fun initRecyclerView() {
            with(recyclerView) {
                layoutManager = GridLayoutManager(context, 3)
                adapter = payTypeAdapter
//                addItemDecoration(GridSpanDecoration(Configs.defaultPaddingDp))
            }
            payTypeAdapter.setNewData(PayTypeX.data())
        }
        initRecyclerView()

        ivClose.safeClicks().subscribe {
            dialog.dismiss()
        }
        rtvPay.safeClicks().subscribe {
            RxBus.post(payTypeAdapter.data.firstOrNull { it.isChecked }!!.payType)
            dialog.dismiss()
        }
    }

    override val containerView = this

}