package com.unicorn.tickets.app.helper

import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.AppUtils
import com.kaopiz.kprogresshud.KProgressHUD
import com.unicorn.tickets.app.Global
import com.unicorn.tickets.app.Key
import com.unicorn.tickets.app.di.ComponentHolder
import com.unicorn.tickets.app.observeOnMain
import com.unicorn.tickets.app.startAct
import com.unicorn.tickets.ui.act.car.BatteryCarAct
import com.unicorn.tickets.ui.act.main.ScanTicketCodeAct
import com.unicorn.tickets.ui.act.main.MenuAct
import com.zhy.http.okhttp.OkHttpUtils
import com.zhy.http.okhttp.callback.FileCallBack
import io.reactivex.rxkotlin.subscribeBy
import okhttp3.Call
import java.io.File

object UpdateHelper {

    fun checkVersion(activity: AppCompatActivity) {
        val api = ComponentHolder.appComponent.api()
        api.checkVersion()
            .observeOnMain(activity)
            .subscribeBy(
                onSuccess = {
                    if (it.version != AppUtils.getAppVersionName())
                        download(activity = activity, apkUrl = it.url)
                    else
                        activity.startAct(
                            when (Global.loginResponse.user.roleTag) {
                                "002" -> ScanTicketCodeAct::class.java
                                "003" -> BatteryCarAct::class.java
                                else -> MenuAct::class.java
                            }
                        )
                },
                onError = {

                }
            )
    }

    private fun download(activity: AppCompatActivity, apkUrl: String) {
        val mask = KProgressHUD.create(activity)
            .setStyle(KProgressHUD.Style.BAR_DETERMINATE)
            .setCancellable(true)
            .setDimAmount(0.5f)
            .setMaxProgress(100)
            .show()
        OkHttpUtils
            .get()
            .url(apkUrl)
            .addHeader(Key.Cookie, "${Key.SESSION}=${Global.session}")
            .build()
            .execute(object : FileCallBack(
                activity.cacheDir.path,
                "Tickets.apk"
            ) {
                override fun onResponse(response: File, id: Int) {
                    mask.dismiss()
                    AppUtils.installApp(response)
                }

                override fun inProgress(progress: Float, total: Long, id: Int) {
                    val p = (100 * progress).toInt()
                    mask.setProgress(p)
                }

                override fun onError(call: Call?, e: Exception?, id: Int) {
                    mask.dismiss()
                }
            })
    }

}