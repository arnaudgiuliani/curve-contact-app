package com.imaginecurve.curvecontactsapp.di

import com.imaginecurve.curvecontactsapp.util.coroutines.SchedulerProvider
import com.imaginecurve.curvecontactsapp.util.coroutines.TestSchedulerProvider
import org.koin.dsl.module.module

val testSchedulerModule = module(override = true) {
    single<SchedulerProvider> { TestSchedulerProvider() }
}