package com.imaginecurve.curvecontactsapp.util.coroutines

import kotlinx.coroutines.experimental.android.UI

/**
 * Application providers
 */
class ApplicationSchedulerProvider : SchedulerProvider {
    override fun ui() = UI
}