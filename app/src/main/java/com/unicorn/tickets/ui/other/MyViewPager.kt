package com.unicorn.tickets.ui.other

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class MyViewPager:ViewPager{
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }

}