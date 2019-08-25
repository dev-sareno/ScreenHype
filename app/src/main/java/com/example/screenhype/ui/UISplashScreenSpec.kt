package com.example.screenhype.ui

import android.graphics.Color
import android.text.Layout
import android.util.Log
import com.example.screenhype.Utils
import com.example.screenhype.controller.AppController
import com.facebook.litho.*
import com.facebook.litho.animation.AnimatedProperties
import com.facebook.litho.annotations.*
import com.facebook.litho.widget.Text
import com.facebook.yoga.YogaAlign
import com.facebook.yoga.YogaEdge
import kotlin.random.Random

@LayoutSpec
object UISplashScreenSpec: UISpec(
    TAG = UISplashScreenSpec::class.java.simpleName
) {

    @OnCreateLayout
    fun onCreateLayout(c: ComponentContext): Component {
        return Column.create(c)
            .also { asPage<UISplashScreenSpec>(it) }
            .transitionKey("page")
            .widthPercent(100f)
            .heightPercent(100f)
            .also { Utils.addBorder(c, it) }
            .child(Text.create(c)
                .marginDip(YogaEdge.TOP, 100f)
                .text("Splash")
                .also {
                    val colors: List<Int> = listOf(Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW, Color.MAGENTA)
                    val randomInt = Random.nextInt(0, 4)
                    it.textColor(colors[randomInt])
                }
                .textSizeDip(35f)
                .textAlignment(Layout.Alignment.ALIGN_CENTER)
                .build())
            .child(Text.create(c)
                .paddingDip(YogaEdge.ALL, 10f)
                .text("Open another page")
                .textSizeDip(25f)
                .textAlignment(Layout.Alignment.ALIGN_CENTER)
                .alignSelf(YogaAlign.CENTER)
                .border(Border.create(c)
                    .color(YogaEdge.ALL, Color.BLUE)
                    .widthDip(YogaEdge.ALL, 1f)
                    .radiusDip(5f)
                    .build())
                .clickHandler(UISplashScreen.onAddClicked(c))
                .build())
            .child(Text.create(c)
                .paddingDip(YogaEdge.ALL, 10f)
                .text("Pop page")
                .textSizeDip(25f)
                .textAlignment(Layout.Alignment.ALIGN_CENTER)
                .alignSelf(YogaAlign.CENTER)
                .border(Border.create(c)
                    .color(YogaEdge.ALL, Color.RED)
                    .widthDip(YogaEdge.ALL, 1f)
                    .radiusDip(5f)
                    .build())
                .clickHandler(UISplashScreen.onPopClicked(c))
                .build())
            .build()
    }

    @OnCreateTransition
    fun onCreateTransition(c: ComponentContext): Transition {
        return Transition.create("page")
            .animate(AnimatedProperties.SCALE)
    }

    @OnEvent(ClickEvent::class)
    fun onAddClicked(c: ComponentContext,
                     @TreeProp appController: AppController
    ) {
        val anotherPage = UISplashScreen.create(c)
            .build()
        appController.push(anotherPage)
    }

    @OnEvent(ClickEvent::class)
    fun onPopClicked(c: ComponentContext,
                     @TreeProp appController: AppController
    ) {
        appController.pop()
        Log.i(TAG, "Page size: ${appController.pages.size}")
    }

}