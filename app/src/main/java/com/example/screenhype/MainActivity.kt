package com.example.screenhype

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.example.screenhype.controller.AppController
import com.example.screenhype.network.apimodels.AppConfigApiModel
import com.example.screenhype.ui.*
import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.LithoView
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable

class MainActivity : AppCompatActivity(), AppController.Impl {

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    private lateinit var mAppController: AppController
    private lateinit var mComponentContext: ComponentContext
    private val mRxAppDisposer = CompositeDisposable()

    private var mHandler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initAppConfig()

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
    override fun getActivity(): MainActivity = this
    override fun onCreateInitialPage(): Component {
        return UISplash.create(mComponentContext)
            .build()
    }

    private fun initAppConfig() {
        val sharedPref = getSharedPreferences(Environment.SHARED_PREF, Context.MODE_PRIVATE)
        val appConfigJson = sharedPref.getString(Environment.SP_KEY_APPCONFIG, null)
        val gson = Gson()
        if (appConfigJson == null) {
            Log.v(TAG, "Saving default AppConfig.")
            val defaultAppConfig = AppConfigApiModel()
            val jsonStr = gson.toJson(defaultAppConfig)
            val editor = sharedPref.edit()
            editor.putString(Environment.SP_KEY_APPCONFIG, jsonStr)
            editor.apply()
            Environment.AppConfig = defaultAppConfig
        } else {
            Log.v(TAG, "Retrieving AppConfig.")
            val appConfig: AppConfigApiModel = gson.fromJson(appConfigJson, AppConfigApiModel::class.java)
            Environment.AppConfig = appConfig
        }
    }

    fun getHandler(): Handler {
        if (mHandler == null)
            mHandler = Handler(mainLooper)
        return mHandler!!
    }

}
