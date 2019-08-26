package com.example.screenhype.network.apimodels

import com.google.gson.annotations.SerializedName

class AppConfigApiModel {
    @SerializedName("splash")
    val splash: AppConfigSplashApiModel = AppConfigSplashApiModel()
}