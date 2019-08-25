package com.example.screenhype.controller

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.facebook.litho.Component
import io.reactivex.subjects.PublishSubject

class AppController(
    private val mImpl: Impl
) {

    interface Impl {
        fun getContext(): Context
        fun getActivity(): AppCompatActivity
        fun onCreateInitialPage(): Component
    }


    val pages: MutableList<Component> = mutableListOf()
    val rxPages: PublishSubject<MutableList<Component>> = PublishSubject.create()

    init {
        val initialPage = mImpl.onCreateInitialPage()
        push(initialPage)
    }

    fun context(): Context = mImpl.getContext()
    fun activity(): AppCompatActivity = mImpl.getActivity()

    // Page Navigation
    fun push(component: Component) {
        pages.add(component)
        rxPages.onNext(pages)
    }
    fun pop() {
        if (pages.size == 1) return
        pages.removeAt(pages.lastIndex)
        rxPages.onNext(pages)
    }

}