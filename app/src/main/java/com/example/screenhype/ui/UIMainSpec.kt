package com.example.screenhype.ui

import android.util.Log
import com.example.screenhype.controller.AppController
import com.facebook.litho.*
import com.facebook.litho.animation.AnimatedProperties
import com.facebook.litho.annotations.*
import com.facebook.yoga.YogaPositionType
import io.reactivex.disposables.CompositeDisposable

@LayoutSpec
object UIMainSpec: UISpec(
    TAG = UIMainSpec::class.java.simpleName
) {

    @OnCreateInitialState
    fun onCreateInitialState(c: ComponentContext,
                             @Prop appController: AppController,
                             @Prop rxAppDisposer: CompositeDisposable,
                             pages: StateValue<MutableList<Component>>
    ) {
        pages.set(appController.pages)

        val disposable = appController.rxPages
            .subscribe( {
                UIMain.onUpdatePages(c, it)
            }, {
                Log.e(TAG, it.message)
            })
        rxAppDisposer.add(disposable)
    }

    @OnCreateTreeProp
    fun onCreateTreePropAppController(c: ComponentContext,
                                      @Prop appController: AppController
    ): AppController = appController

    @OnCreateTreeProp
    fun onCreateTreePropRxAppDisposer(c: ComponentContext,
                                      @Prop rxAppDisposer: CompositeDisposable
    ): CompositeDisposable = rxAppDisposer

    @OnCreateLayout
    fun onCreateLayout(c: ComponentContext,
                       @State pages: MutableList<Component>
    ): Component {
        return Column.create(c)
            .heightPercent(100f)
            .backgroundRes(android.R.color.white)
            .also { uiBuilder ->
                for (page in pages) {
                    uiBuilder.child(page)
                }
            }
//            .clickHandler(UIMain.onComponentClicked(c, null))
            .build()
    }

//    @OnEvent(ClickEvent::class)
//    fun onComponentClicked(c: ComponentContext,
//                           @Param component: Component?
//    ) {
//        if (component != null && !component.hasClickHandlerSet()) {
//            Log.v(TAG, "Component clicked")
//        }
//    }

//    @OnCreateTransition
//    fun onCreateTransition(c: ComponentContext,
//                           @State pages: MutableList<Component>
//    ): Transition {
//        // We animate the last page only
//        val lastChildIndex = pages.size - 1
//        return Transition.create("aaa")
//            .animate(AnimatedProperties.X)
//    }

    @OnUpdateState
    fun onUpdatePages(@Param param: MutableList<Component>,
                      pages: StateValue<MutableList<Component>>
    ) {
        pages.set(param)
    }

}