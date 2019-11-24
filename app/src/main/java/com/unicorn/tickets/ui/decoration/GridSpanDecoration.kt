package com.unicorn.tickets.ui.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

// https://stackoverflow.com/questions/45660968/recyclerview-itemdecoration-spacing-and-span
class GridSpanDecoration(private val padding: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val gridLayoutManager = parent.layoutManager as GridLayoutManager
        val position = gridLayoutManager.getPosition(view)

        val spanCount = gridLayoutManager.spanCount

        val params = view.layoutParams as GridLayoutManager.LayoutParams

        val spanIndex = params.spanIndex
        val spanSize = params.spanSize

        // If it is in column 0 you apply the full offset on the start side, else only half
        if (spanIndex == 0) {
            outRect.left = padding
        } else {
            outRect.left = padding / 2
        }

        // If spanIndex + spanSize equals spanCount (it occupies the last column) you apply the full offset on the end, else only half.
        if (spanIndex + spanSize == spanCount) {
            outRect.right = padding
        } else {
            outRect.right = padding / 2
        }

        // just add some vertical padding as well

        outRect.top = padding / 2
        outRect.bottom = padding / 2

        // my code for top and bottom
        if (position in 0 until spanCount) outRect.top = padding
        if (position == gridLayoutManager.itemCount - 2 || position == gridLayoutManager.itemCount - 1)
            outRect.bottom = padding
    }

}