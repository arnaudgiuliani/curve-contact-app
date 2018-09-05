package com.imaginecurve.curvecontactsapp.util.mvvm

import kotlinx.coroutines.experimental.CoroutineDispatcher

/**
 * Rx Scheduler Provider
 */
interface SchedulerProvider {
    fun ui(): CoroutineDispatcher
}