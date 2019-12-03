package com.jizhe7550.myapplication.app

import android.app.Application
import android.content.Context
import com.jizhe7550.myapplication.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import kotlin.properties.Delegates

class App : Application() {

    companion object {
        var CONTEXT: Context by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        CONTEXT = applicationContext

        startKoin {
            androidContext(this@App)
            modules(appModule)
        }
    }
}