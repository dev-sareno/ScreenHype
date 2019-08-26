package com.example.screenhype.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiManager {

    private var retrofit: Retrofit? = null
    private fun getRetrofit(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl("https://us-central1-android-screen-hype.cloudfunctions.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }

    fun apiService(): ApiService {
        return getRetrofit().create(ApiService::class.java)
    }
}