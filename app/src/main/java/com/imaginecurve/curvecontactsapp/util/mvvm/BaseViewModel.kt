package com.imaginecurve.curvecontactsapp.util.mvvm

import android.arch.lifecycle.ViewModel
import com.imaginecurve.curvecontactsapp.util.coroutines.SchedulerProvider
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Job

/**
 * ViewModel for Coroutines Jobs
 *
 * launch() - launch a Rx request
 * clear all request on stop
 */
abstract class BaseViewModel(private val schedulerProvider: SchedulerProvider) : ViewModel() {

    private var jobs = listOf<Job>()

    fun launch(code: suspend CoroutineScope.() -> Unit) {
        jobs += kotlinx.coroutines.experimental.launch(schedulerProvider.ui(), block = code)
    }

    public override fun onCleared() {
        super.onCleared()
        jobs.forEach { it.cancel() }
    }
}