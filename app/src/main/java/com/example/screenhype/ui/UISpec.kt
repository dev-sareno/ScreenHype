package com.example.screenhype.ui

import com.facebook.litho.Component
import com.facebook.yoga.YogaPositionType

open class UISpec (
    protected val TAG: String = UISpec::class.java.name
) {

    inline fun <reified T: Any> asPage(componentBuilder: Component.Builder<*>) {
        componentBuilder.key(T::class.java.name)
        componentBuilder.widthPercent(100f)
        componentBuilder.heightPercent(100f)
        componentBuilder.positionType(YogaPositionType.ABSOLUTE)
        componentBuilder.backgroundRes(android.R.color.white)
    }

}