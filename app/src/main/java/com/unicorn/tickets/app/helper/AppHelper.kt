package com.unicorn.tickets.app.helper

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.ConvertUtils
import com.unicorn.tickets.R

object AppHelper{


    fun getDashBackground(context: Context):Drawable{
        val md400 = ContextCompat.getColor(context, R.color.md_grey_400)
        return GradientDrawable().apply {
            val width = ConvertUtils.dp2px(1f)
            val dashWidth = ConvertUtils.dp2px(4f).toFloat()
            val dashGap = ConvertUtils.dp2px(4f).toFloat()
            setStroke(width, md400, dashWidth, dashGap)
            setColor(Color.WHITE)
        }
    }
}