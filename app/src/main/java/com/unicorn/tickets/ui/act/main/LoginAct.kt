package com.unicorn.tickets.ui.act.main

import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.PhoneUtils
import com.blankj.utilcode.util.ToastUtils
import com.github.florent37.rxsharedpreferences.RxSharedPreferences
import com.unicorn.tickets.R
import com.unicorn.tickets.app.*
import com.unicorn.tickets.app.helper.DialogHelper
import com.unicorn.tickets.app.helper.ExceptionHelper
import com.unicorn.tickets.app.helper.UpdateHelper
import com.unicorn.tickets.ui.base.BaseAct
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.act_login.*

class LoginAct : BaseAct() {

    override fun initViews() {
        fun restoreUserInfo() {
            RxSharedPreferences.with(this).apply {
                getBoolean(Key.KeepPwd, false).subscribe { keepPwd ->
                    cbKeepPwd.isChecked = keepPwd
                    if (keepPwd) {
                        getString(Key.Username, "").subscribe { etUsername.setText(it) }
                        getString(Key.Password, "").subscribe { etPassword.setText(it) }
                    }
                }
            }
        }
        restoreUserInfo()
    }

    override fun bindIntent() {
        rtvLogin.safeClicks().subscribe { checkDevice() }

        tvChangeCheckBaseUrl.safeClicks().subscribe {
            val items = listOf(Configs.remoteCheckinBaseUrl, Configs.localCheckinBaseUrl)
            val initialSelection = items.indexOf(AppInfo.checkBaseUrl)
            MaterialDialog(this).show {
                title(text = "请选择检票服务器地址，切换后将会自动重启应用")
                listItemsSingleChoice(
                    items = items,
                    initialSelection = initialSelection
                ) { _, _, text ->
                    AppInfo.checkBaseUrl = text
                    AppUtils.relaunchApp(true)
                }
            }
        }
    }

    private fun checkDevice(){
        api.checkDevice(serialNumber = PhoneUtils.getSerial())
            .observeOnMain(this)
            .subscribeBy(
                onSuccess = {
//                    if (it.failed) return@subscribeBy
                    login()
                },
                onError = {
                    ExceptionHelper.showPrompt(it)
                }
            )
    }

    private fun login() {
        if (etUsername.isEmpty()) {
            ToastUtils.showShort("工号不能为空")
            return
        }
        if (etPassword.isEmpty()) {
            ToastUtils.showShort("密码不能为空")
            return
        }
        loginReal()
    }

    private fun showCaptchaDialog(){
        MaterialDialog(this).show {
            customView(R.layout.dialog_captcha)
        }
    }

    private fun loginReal() {
        fun saveUserInfo() {
            RxSharedPreferences.with(this).apply {
                putString(Key.Username, etUsername.trimText()).subscribe { }
                putString(Key.Password, etPassword.trimText()).subscribe { }
                putBoolean(Key.KeepPwd, cbKeepPwd.isChecked).subscribe { }
            }
        }

        val mask = DialogHelper.showMask(this)
        api.login(etUsername.trimText(), etPassword.trimText())
            .observeOnMain(this)
            .subscribeBy(
                onSuccess = {
                    mask.dismiss()

                    // 90天修改一次密码（快过期时，提示用户修改）
                    if (it.success) ToastUtils.showLong(it.tips)

                    if (it.failed) return@subscribeBy
                    Global.loginResponse = it
                    saveUserInfo()
                    UpdateHelper.checkVersion(this)
                },
                onError = {
                    mask.dismiss()
                    ExceptionHelper.showPrompt(it)
                }
            )
    }

    override val layoutId = com.unicorn.tickets.R.layout.act_login

}