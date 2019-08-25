package com.example.screenhype.ui

import android.graphics.Color
import com.facebook.litho.*
import com.facebook.litho.animation.AnimatedProperties
import com.facebook.litho.annotations.*
import kotlin.random.Random

@LayoutSpec
object UIExperimentSpec {

    @OnCreateInitialState
    fun onCreateInitialState(c: ComponentContext,
                             pages: StateValue<MutableList<Component>>
    ) {
        val lPages: MutableList<Component> = mutableListOf()
        lPages.add(UIExperimentAbsolute.create(c)
            .key(lPages.lastIndex.toString())
            .transitionKey(lPages.lastIndex.toString())
            .index(lPages.lastIndex)
            .color(getNextColor())
            .build())
        pages.set(lPages)
    }

    @OnCreateLayout
    fun onCreateLayout(c: ComponentContext,
                       @State pages: MutableList<Component>
    ): Component {
        return Column.create(c)
            .heightPercent(100f)
            .also {
                for (page in pages) { it.child(page) }
            }
            .clickHandler(UIExperiment.onAddAbsoluteComponentClicked(c, pages))
            .build()
    }

    @OnEvent(ClickEvent::class)
    fun onAddAbsoluteComponentClicked(c: ComponentContext, @Param pPages: MutableList<Component>) {
        pPages.add(UIExperimentAbsolute.create(c)
            .key(pPages.lastIndex.toString())
            .transitionKey(pPages.lastIndex.toString())
            .index(pPages.lastIndex)
            .color(getNextColor())
            .build())
        UIExperiment.updatePages(c, pPages)
    }

    @OnUpdateState
    fun updatePages(@Param param: MutableList<Component>, pages: StateValue<MutableList<Component>>) {
        pages.set(param)
    }

    @OnCreateTransition
    fun onCreateTransition(c: ComponentContext,
                           @State pages: MutableList<Component>
    ): Transition {
        return Transition.create("1", "3", "5")
            .animate(AnimatedProperties.X,
                AnimatedProperties.Y,
                AnimatedProperties.HEIGHT)
    }

    private fun getNextColor(): Int {
        val colors: List<Int> = listOf(
            Color.parseColor("#1E8449"),
            Color.parseColor("#922B21"),
            Color.parseColor("#A04000"),
            Color.parseColor("#117A65"),
            Color.parseColor("#9A7D0A")
        )
        val randomInt = Random.nextInt(0, colors.lastIndex)
        return colors[randomInt]
    }

}