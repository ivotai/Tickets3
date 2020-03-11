package com.unicorn.tickets.ui.act.buyTicket

import android.content.Context
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.ConvertUtils
import com.unicorn.tickets.R
import com.unicorn.tickets.app.Global
import com.unicorn.tickets.app.Key
import com.unicorn.tickets.app.RxBus
import com.unicorn.tickets.app.di.ComponentHolder
import com.unicorn.tickets.app.helper.PrintHelper
import com.unicorn.tickets.app.observeOnMain
import com.unicorn.tickets.data.event.PrintGroupApplyEvent
import com.unicorn.tickets.ui.adapter.pager.BuyTicketPagerAdapter
import com.unicorn.tickets.ui.base.BaseAct
import io.reactivex.functions.Consumer
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.act_buy_ticket.*
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView

class BuyTicketAct : BaseAct() {

    override fun initViews() {
        fun initViewPager() = with(viewPaper) {
            adapter = BuyTicketPagerAdapter(
                supportFragmentManager
            ).apply {
                offscreenPageLimit = count - 1
            }
            currentItem = buyTicketIndex
        }
        fun initMagicIndicator() {
            val commonNavigator = CommonNavigator(this).apply {
                isAdjustMode = true
                adapter = object : CommonNavigatorAdapter() {

                    override fun getCount() = BuyTicketPagerAdapter.titles.size

                    override fun getTitleView(context: Context, index: Int) =
                        ColorTransitionPagerTitleView(context).apply {
                            text = BuyTicketPagerAdapter.titles[index]
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
                dividerDrawable = ContextCompat.getDrawable(this@BuyTicketAct, R.drawable.simple_splitter)
            }

            ViewPagerHelper.bind(magicIndicator, viewPaper)
        }

        titleBar.setTitle("景区售票")
        // 获取库存
        fun getInventory() {
            ComponentHolder.appComponent.api()
                .getInventory()
                .observeOnMain(this)
                .subscribeBy {
                    Global.inventory = it
                    initViewPager()
                    initMagicIndicator()
                }
        }
         getInventory()
    }

    override fun registerEvent() {
        RxBus.registerEvent(this,PrintGroupApplyEvent::class.java, Consumer {
            PrintHelper.bindService(this)
            PrintHelper.printGroupApply(it.list)
            PrintHelper.unBindService(this)
        })
    }

    private val blue600 by lazy { ContextCompat.getColor(this, R.color.md_blue_600) }
    private val grey800 by lazy { ContextCompat.getColor(this, R.color.md_grey_700) }

    override val layoutId = R.layout.act_buy_ticket

    private val buyTicketIndex by lazy { intent.getIntExtra(Key.BuyTicketIndex, 0) }

}