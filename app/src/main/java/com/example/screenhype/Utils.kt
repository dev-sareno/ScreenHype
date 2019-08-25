package com.example.screenhype

import android.graphics.Color
import com.facebook.litho.Border
import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.yoga.YogaEdge

object Utils {

    fun addBorder(c: ComponentContext,
                  component: Component.Builder<*>,
                  color: Int = Color.RED
    ) {
        component.border(Border.create(c)
            .color(YogaEdge.ALL, color)
            .widthDip(YogaEdge.ALL, 1f)
            .build())
    }

}