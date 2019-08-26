package com.example.screenhype.ui

import android.content.Context
import android.graphics.Color
import android.util.Log
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.LottieCompositionFactory
import com.airbnb.lottie.LottieDrawable
import com.example.screenhype.Environment
import com.example.screenhype.controller.AppController
import com.example.screenhype.network.ApiManager
import com.example.screenhype.network.apimodels.AppConfigApiModel
import com.example.screenhype.ui.common.UILottieAnimationView
import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.InvisibleEvent
import com.facebook.litho.StateValue
import com.facebook.litho.annotations.*
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@LayoutSpec
object UISplashSpec {

    class Model (
        var apiGetConfig: Call<AppConfigApiModel>? = null
    )

    val TAG: String = UISplashSpec::class.java.simpleName

    @OnCreateInitialState
    fun onCreateInitialState(c: ComponentContext,
                             @TreeProp appController: AppController,
                             model: StateValue<Model>,
                             animComposition: StateValue<LottieComposition?>,
                             bgColor: StateValue<Int>
    ) {
        val uiModel = Model()
        model.set(uiModel)

        val splashConfig = Environment.AppConfig.splash
        val lAnimComposition = LottieCompositionFactory.fromJsonStringSync(splashConfig.lottieAnimRawJsonString, null).value
        animComposition.set(lAnimComposition)
        val lBgColor = Color.parseColor(splashConfig.backgroundColor)
        bgColor.set(lBgColor)

        uiModel.apiGetConfig = ApiManager.apiService().getConfig()
        uiModel.apiGetConfig!!.enqueue(object : Callback<AppConfigApiModel> {
            override fun onResponse(call: Call<AppConfigApiModel>, response: Response<AppConfigApiModel>) {
                if (response.isSuccessful) {
                    val appConfig: AppConfigApiModel = response.body()?: return
                    val appConfigJson = Gson().toJson(appConfig)
                    val sharedPref = appController.context().getSharedPreferences(Environment.SHARED_PREF, Context.MODE_PRIVATE)
                    val editor = sharedPref.edit()
                    editor.putString(Environment.SP_KEY_APPCONFIG, appConfigJson)
                    editor.apply()
                    Log.v(TAG, "AppConfig saved.")

                    appController.activity()
                        .getHandler()
                        .postDelayed({
                            val nextPage = UISplashScreen.create(c)
                                .build()
                            appController.pushAsBase(nextPage)
                        }, 2000)
                }
            }

            override fun onFailure(call: Call<AppConfigApiModel>, t: Throwable) {
                Log.e(TAG, "Api error. err=${t.localizedMessage}")
            }
        })
    }

    @OnCreateLayout
    fun onCreateLayout(c: ComponentContext,
                       @State model: Model,
                       @State animComposition: LottieComposition?,
                       @State bgColor: Int
    ): Component {
        return UILottieAnimationView.create(c)
            .widthPercent(100f)
            .heightPercent(100f)
            .animComposition(animComposition)
            .bgColor(bgColor)
            .repeatCount(LottieDrawable.INFINITE)
            .invisibleHandler(UISplash.onUiInvisible(c, model))
            .build()
    }

    @OnEvent(InvisibleEvent::class)
    fun onUiInvisible(c: ComponentContext, @Param model: Model) {
        if (model.apiGetConfig?.isExecuted == true) {
            model.apiGetConfig?.cancel()
        }
    }

}