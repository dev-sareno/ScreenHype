package com.example.screenhype.ui

import android.graphics.Color
import com.facebook.litho.*
import com.facebook.litho.animation.AnimatedProperties
import com.facebook.litho.annotations.*
import com.facebook.litho.widget.SolidColor
import com.facebook.litho.widget.Text
import com.facebook.yoga.YogaAlign

@LayoutSpec
object UIAnimatedChildSpec {

    @OnCreateInitialState
    fun onCreateInitialState(c: ComponentContext,
                              posLeft: StateValue<Boolean>
    ) {
        posLeft.set(false)
    }

    @OnCreateLayout
    fun onCreateLayout(c: ComponentContext,
                       @State posLeft: Boolean
    ): Component {
        return Column.create(c)
            .heightPercent(100f)
            .child(
                SolidColor.create(c)
                .transitionKey("pos")
                .widthDip(100f)
                .heightDip(100f)
                .alignSelf(if (posLeft) YogaAlign.FLEX_START else YogaAlign.FLEX_END)
                .backgroundColor(Color.parseColor("#7B241C"))
                .build())
            .clickHandler(UIAnimatedChild.onPageClicked(c, posLeft))
            .build()
    }

    @OnEvent(ClickEvent::class)
    fun onPageClicked(c: ComponentContext, @Param pPosLeft: Boolean) {
        UIAnimatedChild.updatePosLeft(c, !pPosLeft)
    }

    @OnUpdateState
    fun updatePosLeft(@Param param: Boolean,  posLeft: StateValue<Boolean>) {
        posLeft.set(param)
    }

    @OnCreateTransition
    fun onCreateTransition(c: ComponentContext
    ): Transition {
        return Transition.create("pos")
            .animate(AnimatedProperties.X)
    }

}