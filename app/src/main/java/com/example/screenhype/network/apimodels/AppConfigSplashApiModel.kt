package com.example.screenhype.network.apimodels

import com.google.gson.annotations.SerializedName

class AppConfigSplashApiModel {
    @SerializedName("backgroundColor")
    val backgroundColor: String? = null

    @SerializedName("lottieAnimUrl")
    val lottieAnimUrl: String? = null

    @SerializedName("lottieAnimRawJsonString")
    val lottieAnimRawJsonString: String? = null

    @SerializedName("lottieRepeatCount")
    val lottieRepeatCount: Int = -1
}