package com.example.screenhype

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.screenhype.controller.AppController
import com.example.screenhype.ui.*
import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.LithoView
import io.reactivex.disposables.CompositeDisposable

class MainActivity : AppCompatActivity(), AppController.Impl {

    private lateinit var mAppController: AppController
    private lateinit var mComponentContext: ComponentContext
    private val mRxAppDisposer = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mComponentContext = ComponentContext(this)
        mAppController = AppController(this)

        val component = UIMain.create(mComponentContext)
            .appController(mAppController)
            .rxAppDisposer(mRxAppDisposer)
            .build()
        setContentView(LithoView.create(mComponentContext, component))
    }

    override fun onDestroy() {
        super.onDestroy()

        mRxAppDisposer.dispose()
    }

    override fun getContext(): Context = this
    override fun getActivity(): AppCompatActivity = this
    override fun onCreateInitialPage(): Component {
        return UISplash.create(mComponentContext)
            .build()
    }

}
