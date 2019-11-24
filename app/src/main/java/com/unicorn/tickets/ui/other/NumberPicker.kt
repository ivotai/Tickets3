package com.unicorn.tickets.ui.other

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.IntRange
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.ConvertUtils
import com.jakewharton.rxbinding3.view.clicks
import com.unicorn.tickets.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.number_picker.view.*

class NumberPicker(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs),
    LayoutContainer {

    override val containerView = this
    private var mValue: Int = 0

    init {
        LayoutInflater.from(context).inflate(R.layout.number_picker, this, true)
        background = GradientDrawable().apply {
            cornerRadius = ConvertUtils.dp2px(10f).toFloat()
            setStroke(1, ContextCompat.getColor(context, R.color.md_grey_300))
        }

        flMinus.clicks().subscribe {
            if (mValue != 0) setValue(mValue - 1)
        }
        flPlus.clicks().subscribe {
            if (mValue != 9) setValue(mValue + 1)
        }
    }

    fun setValue(@IntRange(from = 0, to = 9) value: Int) {
        mValue = value
        tvNumber.text = mValue.toString()
        refresh()
    }

    private fun refresh() {
        val idRes =
            if (mValue == 0) R.drawable.ic__ionicons_svg_md_remove_grey else R.drawable.ic__ionicons_svg_md_remove
        ivMinus.setImageDrawable(ContextCompat.getDrawable(context, idRes))

        val idRes2 =
            if (mValue == 9) R.drawable.ic__ionicons_svg_md_add_grey else R.drawable.ic__ionicons_svg_md_add
        ivPlus.setImageDrawable(ContextCompat.getDrawable(context, idRes2))

    }
}