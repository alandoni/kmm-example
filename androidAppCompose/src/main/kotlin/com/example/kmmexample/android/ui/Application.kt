package com.example.kmmexample.android.ui

import android.app.Application
import com.example.kmmexample.di.KoinInit
import org.koin.android.ext.koin.androidContext

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        KoinInit().start {
            androidContext(this@App)
        }
    }
}