package com.unicorn.tickets.ui.act.team

import android.content.Context
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.ConvertUtils
import com.jakewharton.rxbinding3.widget.textChanges
import com.unicorn.tickets.R
import com.unicorn.tickets.app.RxBus
import com.unicorn.tickets.app.trimText
import com.unicorn.tickets.data.event.GroupApplyRefreshEvent
import com.unicorn.tickets.ui.adapter.pager.TeamPagerAdapter
import com.unicorn.tickets.ui.base.BaseAct
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.act_team.*
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView
import java.util.concurrent.TimeUnit

class TeamAct : BaseAct() {

    override val layoutId = R.layout.act_team

    override fun initViews() {
        fun initViewPager() = with(viewPaper) {
            adapter = TeamPagerAdapter(
                supportFragmentManager
            ).apply {
                offscreenPageLimit = count - 1
            }
        }

        fun initMagicIndicator() {
            val commonNavigator = CommonNavigator(this).apply {
                isAdjustMode = true
                adapter = object : CommonNavigatorAdapter() {

                    override fun getCount() = TeamPagerAdapter.titles.size

                    override fun getTitleView(context: Context, index: Int) =
                        ColorTransitionPagerTitleView(context).apply {
                            text = TeamPagerAdapter.titles[index]
                            textSize = 18f
                            normalColor = grey800
                            selectedColor = grey800
                            setOnClickListener { viewPaper.currentItem = index }
                        }

                    override fun getIndicator(context: Context) =
                        LinePagerIndicator(context).apply {
                            setColors(blue600)
                            lineHeight = ConvertUtils.dp2px(4f).toFloat()
                            xOffset = ConvertUtils.dp2px(12f).toFloat()
                        }

                    override fun getTitleWeight(context: Context?, index: Int) = 1.0f

                }
            }

            magicIndicator.navigator = commonNavigator

            // must after setNavigator
            with(commonNavigator.titleContainer) {
                showDividers = LinearLayout.SHOW_DIVIDER_MIDDLE
                dividerPadding = ConvertUtils.dp2px(16f)
                dividerDrawable =
                    ContextCompat.getDrawable(this@TeamAct, R.drawable.simple_splitter)
            }

            ViewPagerHelper.bind(magicIndicator, viewPaper)
        }
        titleBar.setTitle("团队登记")
        initViewPager()
        initMagicIndicator()
    }

    override fun bindIntent() {
        etKeyword.textChanges()
            .debounce(500, TimeUnit.MILLISECONDS)
            .filter { it.isNotBlank() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                RxBus.post(GroupApplyRefreshEvent(etKeyword.trimText()))
            }
    }



    private val blue600 by lazy { ContextCompat.getColor(this, R.color.md_blue_600) }
    private val grey800 by lazy { ContextCompat.getColor(this, R.color.md_grey_700) }

}