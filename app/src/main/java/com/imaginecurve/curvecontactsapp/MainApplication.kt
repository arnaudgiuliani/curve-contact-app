package com.imaginecurve.curvecontactsapp

import android.app.Application
import com.imaginecurve.curvecontactsapp.di.appModule
import com.imaginecurve.curvecontactsapp.di.dataModule
import org.koin.android.ext.android.startKoin


class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Mock version
//        startKoin(this, listOf(appModule, mockedDataModule))

        // Star Koin for DI
        startKoin(this, listOf(appModule, dataModule))

    }
}

// Log Flag
const val APP_TAG = "CurveContactsApp"