package com.imaginecurve.curvecontactsapp.util.coroutines

import kotlinx.coroutines.experimental.CoroutineDispatcher

/**
 * Rx Scheduler Provider
 */
interface SchedulerProvider {
    fun ui(): CoroutineDispatcher
}