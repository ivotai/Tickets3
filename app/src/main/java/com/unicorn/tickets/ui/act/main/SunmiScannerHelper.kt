package com.unicorn.tickets.ui.act.main

import android.app.Service
import android.content.*
import android.os.IBinder
import com.blankj.utilcode.util.ActivityUtils
import com.sunmi.scanner.IScanInterface

class SunmiScannerHelper(private val context: Context, private var listener: ScanListener) {

    private var iScanInterface: IScanInterface? = null

    private val serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            iScanInterface = IScanInterface.Stub.asInterface(service)
        }

        override fun onServiceDisconnected(name: ComponentName) {

        }
    }

    private val receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            // 只有当前活动响应扫红外扫码结果
            val top = ActivityUtils.getTopActivity()
            if (top != this@SunmiScannerHelper.context) return
            val result = intent.getStringExtra("data")
            if (result != null) listener.onScanResult(result.replace("]ZP01", ""))
        }
    }

    init {
        registerReceiver()
        bindService()
    }

    interface ScanListener {
        fun onScanResult(result: String)
    }

    val scannerModel: Int
        get() = iScanInterface?.scannerModel ?: -1

    fun scan() {
        iScanInterface?.scan()
    }

    private fun registerReceiver() {
        val filter = IntentFilter()
        filter.addAction("com.sunmi.scanner.ACTION_DATA_CODE_RECEIVED")
        context.registerReceiver(receiver, filter)
    }

    private fun bindService() {
        val intent = Intent()
        intent.setPackage("com.sunmi.scanner")
        intent.action = "com.sunmi.scanner.IScanInterface"
        context.bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE)
    }

    fun release() {
        context.unregisterReceiver(receiver)
        context.unbindService(serviceConnection)
    }

}
