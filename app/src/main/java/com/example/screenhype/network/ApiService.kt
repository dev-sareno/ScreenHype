package com.example.screenhype.network

import com.example.screenhype.network.apimodels.AppConfigApiModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("appConfig")
    fun getConfig(): Call<AppConfigApiModel>
}