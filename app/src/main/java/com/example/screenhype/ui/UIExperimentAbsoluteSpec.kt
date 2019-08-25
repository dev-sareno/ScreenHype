package com.example.screenhype.ui

import android.graphics.Color
import com.example.screenhype.Utils
import com.facebook.litho.*
import com.facebook.litho.animation.AnimatedProperties
import com.facebook.litho.annotations.*
import com.facebook.litho.widget.Text
import com.facebook.yoga.YogaAlign
import com.facebook.yoga.YogaEdge

@LayoutSpec
object UIExperimentAbsoluteSpec {

    @OnCreateInitialState
    fun onCreateInitialState(c: ComponentContext,
                             textPositionLeft: StateValue<Boolean>
    ) {
        textPositionLeft.set(true)
    }

    @OnCreateLayout
    fun onCreateLayout(c: ComponentContext,
                       @Prop index: Int,
                       @Prop color: Int,
                       @State textPositionLeft: Boolean
    ): Component {
        return Column.create(c)
            .backgroundColor(color)
            .child(
                Text.create(c)
                    .also { Utils.addBorder(c, it) }
                    .transitionKey("pos")
                    .text("Absolute component #${index + 1}")
                    .textColor(Color.WHITE)
                    .textSizeDip(35f)
                    .paddingDip(YogaEdge.ALL, 10f)
                    .alignSelf(if (textPositionLeft) YogaAlign.FLEX_START else YogaAlign.FLEX_END)
                    .clickHandler(UIExperimentAbsolute.onChangeTextPosition(c, textPositionLeft))
                    .build())
            .build()
    }

    @OnCreateTransition
    fun onCreateTransition(c: ComponentContext,
                           @Prop index: Int
    ): Transition {
        return Transition.create("pos")
            .animate(AnimatedProperties.X)
    }

    @OnEvent(ClickEvent::class)
    fun onChangeTextPosition(c: ComponentContext,
                             @Param pCurrentPositionIsLeft: Boolean
    ) {
        UIExperimentAbsolute.updateTextPositionIsLeft(c, !pCurrentPositionIsLeft)
    }

    @OnUpdateState
    fun updateTextPositionIsLeft(@Param param: Boolean,
                                 textPositionLeft: StateValue<Boolean>
    ) {
        textPositionLeft.set(param)
    }

}