package com.unicorn.tickets.ui.act.orderSearch

import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jakewharton.rxbinding3.widget.textChanges
import com.unicorn.tickets.R
import com.unicorn.tickets.app.Configs
import com.unicorn.tickets.app.trimText
import com.unicorn.tickets.data.model.Order
import com.unicorn.tickets.ui.adapter.OrderAdapter
import com.unicorn.tickets.ui.base.KVHolder
import com.unicorn.tickets.ui.base.SimplePageAct
import com.unicorn.tickets.ui.decoration.LinearSpanDecoration
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.act_order_search.*
import java.util.concurrent.TimeUnit

class OrderSearchAct : SimplePageAct<Order, KVHolder>() {

    override fun initViews() {
        super.initViews()
        titleBar.setTitle("订单查询")
        mRecyclerView.addItemDecoration(LinearSpanDecoration(Configs.defaultPaddingDp))
    }

    override fun bindIntent() {
        super.bindIntent()
        etKeyword.textChanges()
            .debounce(500, TimeUnit.MILLISECONDS)
            .filter { it.isNotBlank() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { loadFirstPage() }
    }

    override val simpleAdapter: BaseQuickAdapter<Order, KVHolder> = OrderAdapter()

    override fun loadPage(page: Int) = api.getOrderList(keyword = etKeyword.trimText(), page = page)

    override val layoutId = R.layout.act_order_search

    override val mRecyclerView: RecyclerView
        get() = recyclerView

    override val mSwipeRefreshLayout: SwipeRefreshLayout
        get() = swipeRefreshLayout

}