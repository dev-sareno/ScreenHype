package com.example.screenhype.ui

import android.graphics.Color
import android.util.Log
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.LottieCompositionFactory
import com.airbnb.lottie.LottieDrawable
import com.airbnb.lottie.parser.moshi.JsonReader
import com.example.screenhype.R
import com.example.screenhype.network.ApiManger
import com.example.screenhype.network.apimodels.AppConfigApiModel
import com.example.screenhype.ui.common.UILottieAnimationView
import com.example.screenhype.util.JsonUtils
import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.InvisibleEvent
import com.facebook.litho.StateValue
import com.facebook.litho.annotations.*
import org.json.JSONObject
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
                             model: StateValue<Model>,
                             animAsset: StateValue<String>,
                             animComposition: StateValue<LottieComposition?>,
                             bgColor: StateValue<Int>
    ) {
        val uiModel = Model()
        model.set(uiModel)
        animAsset.set(c.getString(R.string.splash_anim_filename))
        animComposition.set(null)
        bgColor.set(c.getColor(android.R.color.black))

        uiModel.apiGetConfig = ApiManger.apiService().getConfig()
        uiModel.apiGetConfig!!.enqueue(object : Callback<AppConfigApiModel> {
            override fun onResponse(call: Call<AppConfigApiModel>, response: Response<AppConfigApiModel>) {
                if (response.isSuccessful) {
                    val appConfig = response.body()?: return
                    val splashConfig = appConfig.splash?: return
                    val animJsonRaw = splashConfig.lottieAnimRawJsonString
                    if (animJsonRaw != null) {
                        if (JsonUtils.isJSONValid(animJsonRaw) && JSONObject(animJsonRaw).length() > 0) {
                            val lAnimComposition = LottieCompositionFactory.fromJsonStringSync(animJsonRaw, null).value
                            UISplash.updateAnimComposition(c, lAnimComposition)
                        }

                    }

                    if (splashConfig.backgroundColor != null) {
                        val lBgColor = Color.parseColor(splashConfig.backgroundColor)
                        UISplash.updateBgColor(c, lBgColor)
                    }
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
                       @State animAsset: String,
                       @State animComposition: LottieComposition?,
                       @State bgColor: Int
    ): Component {
        return UILottieAnimationView.create(c)
            .widthPercent(100f)
            .heightPercent(100f)
            .also { builder ->
                if (animComposition != null) {
                    builder.animComposition(animComposition)
                } else {
                    builder.animationFromAssets(animAsset)
                }
            }
            .bgColor(bgColor)
            .repeatCount(LottieDrawable.INFINITE)
            .invisibleHandler(UISplash.onUiInvisible(c, model))
            .build()
    }

    @OnUpdateState
    fun updateAnimComposition(@Param value: LottieComposition, animComposition: StateValue<LottieComposition?>) {
        animComposition.set(value)
    }

    @OnUpdateState
    fun updateBgColor(@Param value: Int, bgColor: StateValue<Int>) {
        bgColor.set(value)
    }

    @OnEvent(InvisibleEvent::class)
    fun onUiInvisible(c: ComponentContext, @Param model: Model) {
        if (model.apiGetConfig?.isExecuted == true) {
            model.apiGetConfig?.cancel()
        }
    }

}