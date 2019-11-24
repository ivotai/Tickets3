package com.unicorn.tickets.ui.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LinearSpanDecoration(private val padding: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val linearLayoutManager = parent.layoutManager as LinearLayoutManager
        val position = linearLayoutManager.getPosition(view)
        outRect.left = padding
        outRect.right = padding
        outRect.top = padding / 2
        outRect.bottom = padding / 2

        if (position == 0) outRect.top = padding
        if (position == linearLayoutManager.itemCount - 1) outRect.bottom = padding
    }

}