package com.imaginecurve.curvecontactsapp.util.coroutines

import kotlinx.coroutines.experimental.Unconfined

class TestSchedulerProvider : SchedulerProvider {
    override fun ui() = Unconfined
}