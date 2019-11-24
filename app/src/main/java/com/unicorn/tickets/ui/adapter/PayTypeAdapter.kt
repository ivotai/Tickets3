package com.unicorn.tickets.ui.adapter

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.ConvertUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.unicorn.tickets.R
import com.unicorn.tickets.app.safeClicks
import com.unicorn.tickets.data.model.PayTypeX
import com.unicorn.tickets.ui.base.KVHolder
import kotlinx.android.synthetic.main.item_pay_type.*

class PayTypeAdapter : BaseQuickAdapter<PayTypeX, KVHolder>(R.layout.item_pay_type) {

    private val md400 by lazy { ContextCompat.getColor(mContext, R.color.md_grey_400) }
    private val colorPrimary by lazy { ContextCompat.getColor(mContext, R.color.colorPrimary) }

    private val d1 by lazy {
        GradientDrawable().apply {
            val width = ConvertUtils.dp2px(1f)
            val dashWidth = ConvertUtils.dp2px(4f).toFloat()
            val dashGap = ConvertUtils.dp2px(4f).toFloat()
            setStroke(width, md400, dashWidth, dashGap)
            setColor(Color.WHITE)
        }
    }
    private val d2 by lazy {
        GradientDrawable().apply {
            val width = ConvertUtils.dp2px(1f)
            setStroke(width, colorPrimary)
            setColor(Color.WHITE)
        }
    }

    private val d by lazy {
        GradientDrawable().apply {
            setStroke(ConvertUtils.dp2px(1f), ContextCompat.getColor(mContext, R.color.md_blue_600))
            setColor(
                ColorUtils.setAlphaComponent(
                    ContextCompat.getColor(mContext, R.color.md_blue_600),
                    0.1f
                )
            )
        }
    }

    override fun convert(helper: KVHolder, item: PayTypeX) {
        helper.apply {
            tvPayTypeText.text = item.payType.text
            if (item.isChecked) {
                root.background = d2
            } else {
                root.background = d1
            }
            root.safeClicks().subscribe {
                data.forEach { it.isChecked = it == item }
                notifyDataSetChanged()
            }

            ivIcon.setImageResource(item.payType.resId)
        }
    }
}