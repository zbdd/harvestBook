package com.zbdd.harvestbook

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

/**
 * Empty Application class for initialising our Hilt dependency injection
 *
 * @author Zac Durber
 */
@HiltAndroidApp
class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        MyApplication.appContext = applicationContext
    }

    companion object {
        lateinit var appContext: Context
    }
}