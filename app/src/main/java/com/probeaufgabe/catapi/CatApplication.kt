package com.probeaufgabe.catapi

import android.app.Application
import com.probeaufgabe.catapi.data.di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CatApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@CatApplication)
            modules(dataModule)
        }
    }
}