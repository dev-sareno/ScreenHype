package com.example.screenhype

import com.example.screenhype.network.apimodels.AppConfigApiModel

object Environment {
    lateinit var AppConfig: AppConfigApiModel
    const val SHARED_PREF = "ScreenHype"
    const val SP_KEY_APPCONFIG = "AppConfig"
}