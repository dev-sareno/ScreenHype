package com.example.screenhype.ui

import android.graphics.Color
import com.facebook.litho.*
import com.facebook.litho.animation.AnimatedProperties
import com.facebook.litho.annotations.*
import com.facebook.litho.widget.SolidColor

@LayoutSpec
object UIExpScaleAnimSpec {

    @OnCreateInitialState
    fun onCrateInitialState(c: ComponentContext,
                            scale: StateValue<Float>
    ) {
        scale.set(1f)
    }

    @OnCreateLayout
    fun onCreateLayout(c: ComponentContext,
                       @State scale: Float
    ): Component {
    /*    return Column.create(c)
            .heightPercent(100f)
            .child(Column.create(c)
                .transitionKey("anim")
                .widthDip(scale)
                .heightDip(scale)
                .backgroundColor(Color.parseColor("#7B241C"))
                .build())
            .clickHandler(UIExpScaleAnim.onPageClicked(c, scale))
            .build()*/
        return Column.create(c)
            .heightPercent(100f)
            .child(SolidColor.create(c)
                .transitionKey("anim")
                .widthDip(scale)
                .heightDip(scale)
                .color(Color.parseColor("#7B241C"))
                .build())
            .clickHandler(UIExpScaleAnim.onPageClicked(c, scale))
            .build()
    }

    @OnCreateTransition
    fun onCreateTransition(c: ComponentContext
    ): Transition {
        return Transition.create("anim")
            .animate(AnimatedProperties.WIDTH, AnimatedProperties.HEIGHT)
    }

    @OnUpdateState
    fun updateScale(@Param param: Float, scale: StateValue<Float>) {
        scale.set(param)
    }

    @OnEvent(ClickEvent::class)
    fun onPageClicked(c: ComponentContext,
                      @Param pCurrentScale: Float
    ) {
        UIExpScaleAnim.updateScale(c, if (pCurrentScale == 1f) 100f else 1f)
    }

}