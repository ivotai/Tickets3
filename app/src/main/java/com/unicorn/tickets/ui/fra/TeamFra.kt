package com.unicorn.tickets.ui.fra

import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.unicorn.tickets.R
import com.unicorn.tickets.app.Configs
import com.unicorn.tickets.app.Key
import com.unicorn.tickets.app.RxBus
import com.unicorn.tickets.data.event.GroupApplyRefreshEvent
import com.unicorn.tickets.data.model.GroupApply
import com.unicorn.tickets.data.model.base.BaseResponse
import com.unicorn.tickets.data.model.base.PageResponse
import com.unicorn.tickets.ui.adapter.TeamAdapter
import com.unicorn.tickets.ui.base.KVHolder
import com.unicorn.tickets.ui.base.SimplePageFra
import com.unicorn.tickets.ui.decoration.LinearSpanDecoration
import io.reactivex.Single
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.ui_swipe_recycler.*

class TeamFra : SimplePageFra<GroupApply, KVHolder>() {

    override val simpleAdapter: BaseQuickAdapter<GroupApply, KVHolder> = TeamAdapter()

    override fun loadPage(page: Int): Single<BaseResponse<PageResponse<GroupApply>>> {
        return api.getOrderGroupApplyList(keyword, groupApplyStatus, page)
    }

    var keyword = ""

    override val mRecyclerView: RecyclerView
        get() = recyclerView

    override val mSwipeRefreshLayout: SwipeRefreshLayout
        get() = swipeRefreshLayout

    override fun initViews() {
        super.initViews()
        mRecyclerView.addItemDecoration(LinearSpanDecoration(Configs.defaultPaddingDp))
    }

    override fun bindIntent() {
        super.bindIntent()
    }

    override fun registerEvent() {
        RxBus.registerEvent(this, GroupApplyRefreshEvent::class.java, Consumer {
            keyword = it.keyword
            loadFirstPage()
        })
    }

    private val groupApplyStatus by lazy { arguments!!.getInt(Key.GroupApplyStatus) }

    override val layoutId = R.layout.ui_swipe_recycler
}