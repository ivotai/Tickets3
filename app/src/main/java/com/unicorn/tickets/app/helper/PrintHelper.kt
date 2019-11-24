package com.unicorn.tickets.app.helper

import android.content.Context
import android.graphics.BitmapFactory
import com.blankj.utilcode.util.ToastUtils
import com.sunmi.peripheral.printer.InnerPrinterCallback
import com.sunmi.peripheral.printer.InnerPrinterManager
import com.sunmi.peripheral.printer.InnerResultCallbcak
import com.sunmi.peripheral.printer.SunmiPrinterService
import com.unicorn.tickets.R
import com.unicorn.tickets.app.Configs
import com.unicorn.tickets.app.Global
import com.unicorn.tickets.app.RxBus
import com.unicorn.tickets.app.toast
import com.unicorn.tickets.data.event.PrintHelperPreparedEvent
import com.unicorn.tickets.data.event.PrintTicketEvent
import com.unicorn.tickets.data.model.SourceType
import com.unicorn.tickets.data.model.Ticket
import com.unicorn.tickets.data.model.base.GroupProduct
import org.joda.time.DateTime

object PrintHelper {

    fun bindService(context: Context) {
        InnerPrinterManager.getInstance().bindService(context, innerPrinterCallback)
    }

    fun unBindService(context: Context) {
        InnerPrinterManager.getInstance().unBindService(context, innerPrinterCallback)
    }

    fun printGroupApply(list: List<GroupProduct>) {
        val groupApply = Global.groupApply
        val temp = list.filter { it.quantity > 0 }
        var result = ""
        temp.forEach {
            result += "${it.name}*${it.quantity} "
        }


        with(sunmiPrinterService) {
            setFontSize(36f, innerResultCallback)
            printText("团队信息\n", null)

            setAlignment(0, null)
            setFontSize(30f, innerResultCallback)
            printText("*************************\n", null)
            printText("联系人：${groupApply.contact}\n", null)
            printText("电话号码：${groupApply.mobile}\n", null)
            printText(
                "日期：${DateTime(groupApply.travelDate).toString(Configs.displayDateFormat)}\n",
                null
            )
            printText("出票信息：${result}\n", null)

            lineWrap(1, null)
            printText("说明：仅限当日闭园前使用，现场需持有效证件入场\n", null)
            printText("*************************\n", null)
            lineWrap(1, null)

            setAlignment(1, null)

            printQRCode(
                "UB23Whzaaec/T3FP7I9t76jDRv0+66OH7Mvp8kRkD7V47pifsGLuPX/Moq1V6ch0",
                4,
                3,
                null
            )

            lineWrap(4, null)
        }

    }

    fun printTickets(context: Context, tickets: List<Ticket>) {

        val logo = BitmapFactory.decodeResource(context.resources, R.mipmap.logo)

        with(sunmiPrinterService) {
            //            setFontSize(18f, innerResultCallback)
            enterPrinterBuffer(true)
            tickets.forEach {
                                setAlignment(1, null)
//                printBitmapCustom(logo, 2, null)
//
//                lineWrap(2, null)
                setFontSize(36f, innerResultCallback)
                printText("欢迎光临辰山植物园\n", null)

                setAlignment(0, null)
                setFontSize(30f, innerResultCallback)
                printText("*************************\n", null)
                printText("票名：${it.title}\n", null)
                printText("金额：${it.price}元\n", null)
                printText(
                    "出票时间：${DateTime(it.travelDate).toString(Configs.displayDateFormat)}\n",
                    null
                )
                printText("购票渠道：${SourceType.sourceTypeMap[it.sourceType]}\n", null)
                lineWrap(1, null)
                printText("说明：仅限当日闭园前使用${if (it.stype == 4) "" else "，现场需持有效证件入场"}\n", null)
                printText("*************************\n", null)
                lineWrap(1, null)

                setAlignment(1, null)
                // moduleSize 4-16 二维码大小， errorLevel 纠错级别 3 最高
                printQRCode(it.ticketCode, 4, 3, null)

                lineWrap(1, null)
                setFontSize(20f, innerResultCallback)
                printText("****${it.ticketNo}****\n", null)
                lineWrap(4, null)

                val index = tickets.indexOf(it)
                commitPrinterBufferWithCallback(object : MyInnerResultCallback(index) {
                    override fun onRunResult(isSuccess: Boolean) {

                        com.orhanobut.logger.Logger.e("$index")
                    }

                    override fun onPrintResult(code: Int, msg: String?) {
//                        if (code == 0) "$index 事务执行成功".toast()
//                        else "$index 事务执行失败".toast()
//                        com.orhanobut.logger.Logger.e("$index")
                        RxBus.post(PrintTicketEvent(index))
                    }

                    override fun onReturnString(result: String?) {
                        result?.toast()
                    }

                    override fun onRaiseException(code: Int, msg: String?) {
                        msg?.toast()
                    }
                })

            }
            exitPrinterBuffer(true)
        }
    }

    private lateinit var sunmiPrinterService: SunmiPrinterService

    private val innerPrinterCallback = object : InnerPrinterCallback() {
        override fun onConnected(service: SunmiPrinterService) {
//            ToastUtils.showShort("连接打印服务")
            sunmiPrinterService = service
            RxBus.post(PrintHelperPreparedEvent())
        }

        override fun onDisconnected() {
//            ToastUtils.showShort("断开打印服务")
        }
    }

    private val innerResultCallback = object : InnerResultCallbcak() {
        override fun onRunResult(isSuccess: Boolean) {
            // 指令执行结果
//            Logger.e("result $isSuccess")
        }

        override fun onPrintResult(code: Int, msg: String?) {
            msg?.toast()
        }

        override fun onReturnString(result: String?) {
            // 指令执行结果
            result?.toast()
        }

        override fun onRaiseException(code: Int, msg: String?) {
            if (code == 0) "事务执行成功".toast()
            else "事务执行失败".toast()
        }
    }

    abstract class MyInnerResultCallback(val index: Int) : InnerResultCallbcak()


}