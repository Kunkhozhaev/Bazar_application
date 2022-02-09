package com.example.bazar

import android.app.Application
import com.example.bazar.di.dataModule
import com.example.bazar.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        appInstance = this
        val modules = listOf(dataModule, viewModelModule)
        startKoin {
            androidLogger()
            androidContext(this@App)
            androidFileProperties()
            koin.loadModules(modules)
        }
    }

    companion object {
        private lateinit var appInstance: App
        fun getAppInstance(): App {
            return appInstance
        }
    }
}