package com.unicorn.tickets.ui.act.main

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import androidx.recyclerview.widget.GridLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.unicorn.tickets.data.model.Menu
import com.unicorn.tickets.ui.adapter.MenuAdapter
import com.unicorn.tickets.ui.base.BaseAct
import com.unicorn.tickets.ui.decoration.GridSpanDecoration
import kotlinx.android.synthetic.main.ui_title_recycler.*


class MenuAct : BaseAct() {

    override fun initViews() {
        fun initRecyclerView() = with(recyclerView) {
            background = GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                intArrayOf(Color.parseColor("#5A98D3"), Color.parseColor("#2E5F9E"))
            )
            layoutManager = GridLayoutManager(this@MenuAct, 3)
            adapter = menuAdapter
            addItemDecoration(GridSpanDecoration(ConvertUtils.dp2px(24f)))
        }

        titleBar.setTitle("功能菜单")
        initRecyclerView()

    }

    override fun bindIntent() {
        super.bindIntent()
        menuAdapter.setNewData(Menu.all())
    }

    private val menuAdapter = MenuAdapter()

    override val layoutId = com.unicorn.tickets.R.layout.ui_title_recycler


}
