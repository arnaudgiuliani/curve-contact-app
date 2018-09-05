package com.imaginecurve.curvecontactsapp

import android.app.Application
import com.imaginecurve.curvecontactsapp.di.appModule
import com.imaginecurve.curvecontactsapp.di.mockedDataModule
import org.koin.android.ext.android.startKoin


class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(appModule, mockedDataModule))
    }
}

const val APP_TAG = "CurveContactsApp"