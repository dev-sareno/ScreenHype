package com.example.screenhype.ui.common

import android.content.Context
import androidx.annotation.ColorInt
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.parser.moshi.JsonReader
import com.facebook.litho.*
import com.facebook.litho.annotations.*
import java.lang.IllegalArgumentException


@MountSpec(isPureRender = true, poolSize = 0)
object UILottieAnimationViewSpec {

    @OnPrepare
    fun onPrepare(c: ComponentContext,
                  outBgColor: Output<Int>,
                  @Prop(optional = true) bgColor: Int?,
                  outAnimFromUri: Output<String?>,
                  @Prop(optional = true) animationFromUri: String?,
                  outAnimFromAsset: Output<String?>,
                  @Prop(optional = true) animationFromAssets: String?,
                  outAnimFromRawRes: Output<Int?>,
                  @Prop(optional = true) animationFromRawRes: Int?,
                  outAnimFromJsonReader: Output<JsonReader?>,
                  @Prop(optional = true) animComposition: LottieComposition?,
                  outAnimComposition: Output<LottieComposition?>,
                  @Prop(optional = true) animationFromJsonReader: JsonReader?,
                  outCacheKey: Output<String?>,
                  @Prop(optional = true) cacheKey: String?,
                  outRepeatCount: Output<Int>,
                  @Prop repeatCount: Int
    ) {
        outBgColor.set(bgColor?: c.getColor(android.R.color.black))
        outAnimFromUri.set(animationFromUri)
        outAnimFromAsset.set(animationFromAssets)
        outAnimFromRawRes.set(animationFromRawRes)
        outAnimFromJsonReader.set(animationFromJsonReader)
        outRepeatCount.set(repeatCount)
        outAnimComposition.set(animComposition)
        outCacheKey.set(cacheKey)
    }

    @OnCreateMountContent
    fun onCreateMountContent(c: Context): LottieAnimationView {
        return LottieAnimationView(c)
    }

    @OnMount
    fun onMount(c: ComponentContext,
                lottieView: LottieAnimationView,
                @FromPrepare @ColorInt outBgColor: Int,
                @FromPrepare outAnimFromUri: String?,
                @FromPrepare outAnimFromAsset: String?,
                @FromPrepare outAnimFromRawRes: Int?,
                @FromPrepare outAnimFromJsonReader: JsonReader?,
                @FromPrepare outCacheKey: String?,
                @FromPrepare outAnimComposition: LottieComposition?,
                @FromPrepare outRepeatCount: Int
    ) {
        when {
            outAnimFromUri != null -> lottieView.setAnimationFromUrl(outAnimFromUri)
            outAnimFromAsset != null -> lottieView.setAnimation(outAnimFromAsset)
            outAnimFromRawRes != null -> lottieView.setAnimation(outAnimFromRawRes)
            outAnimFromJsonReader != null -> lottieView.setAnimation(outAnimFromJsonReader, outCacheKey)
            outAnimComposition != null -> lottieView.setComposition(outAnimComposition)
            else -> throw IllegalArgumentException("Animation resource is required.")
        }
        lottieView.repeatCount = outRepeatCount
        lottieView.setBackgroundColor(outBgColor)
        lottieView.playAnimation()
    }

    @OnMeasure
    fun onMeasure(
        context: ComponentContext,
        ComponentLayout: ComponentLayout,
        widthSpec: Int,
        heightSpec: Int,
        size: Size
    ) {

        // If width is undefined, set default size.
        if (SizeSpec.getMode(widthSpec) == SizeSpec.UNSPECIFIED) {
            size.width = 40
        } else {
            size.width = SizeSpec.getSize(widthSpec)
        }

        // If height is undefined, use 1.5 aspect ratio.
        if (SizeSpec.getMode(heightSpec) == SizeSpec.UNSPECIFIED) {
            size.height = (size.width * 1.5).toInt()
        } else {
            size.height = SizeSpec.getSize(heightSpec)
        }
    }

    @ShouldUpdate
    fun shouldUpdate(@Prop(optional = true) bgColor: Diff<Int?>,
                     @Prop(optional = true) animationFromUri: Diff<String?>,
                     @Prop(optional = true) animationFromAssets: Diff<String?>,
                     @Prop(optional = true) animationFromRawRes: Diff<Int?>,
                     @Prop(optional = true) animComposition: Diff<LottieComposition?>,
                     @Prop(optional = true) animationFromJsonReader: Diff<JsonReader?>,
                     @Prop(optional = true) cacheKey: Diff<String?>,
                     @Prop repeatCount: Diff<Int>
    ): Boolean {
        if (bgColor.previous != bgColor.next) return true
        if (animationFromUri.previous != animationFromUri.next) return true
        if (animationFromAssets.previous != animationFromAssets.next) return true
        if (animationFromRawRes.previous != animationFromRawRes.next) return true
        if (animComposition.previous != animComposition.next) return true
        if (animationFromJsonReader.previous != animationFromJsonReader.next) return true
        if (cacheKey.previous != cacheKey.next) return true
        if (repeatCount.previous != repeatCount.next) return true
        return false
    }
}