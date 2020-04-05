package com.unicorn.tickets.app

import com.chibatching.kotpref.KotprefModel

object AppInfo : KotprefModel() {
    var checkBaseUrl by stringPref(default = Configs.remoteCheckinBaseUrl)
}