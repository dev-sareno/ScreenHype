package com.example.screenhype.ui

import android.util.Log
import com.example.screenhype.controller.AppController
import com.facebook.litho.Component
import com.facebook.litho.Transition
import com.facebook.yoga.YogaPositionType

open class UISpec (
    protected val TAG: String = UISpec::class.java.name
) {

    inline fun <reified T: Any> asPage(appController: AppController, componentBuilder: Component.Builder<*>) {
//        Log.d("XXX", "XXX-page-position-${appController.pages.size - 1}")
        componentBuilder.key(T::class.java.name)
        componentBuilder.widthPercent(100f)
        componentBuilder.heightPercent(100f)
//        componentBuilder.transitionKey("page-position-${appController.pages.size - 1}")
//        componentBuilder.transitionKeyType(Transition.TransitionKeyType.GLOBAL)
        componentBuilder.positionType(YogaPositionType.ABSOLUTE)
        componentBuilder.backgroundRes(android.R.color.white)
    }

}