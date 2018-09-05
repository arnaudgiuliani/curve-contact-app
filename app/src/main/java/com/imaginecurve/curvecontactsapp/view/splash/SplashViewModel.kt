package com.imaginecurve.curvecontactsapp.view.splash

import android.arch.lifecycle.LiveData
import com.imaginecurve.curvecontactsapp.domain.ContactRepository
import com.imaginecurve.curvecontactsapp.util.coroutines.SchedulerProvider
import com.imaginecurve.curvecontactsapp.util.mvvm.*

class SplashViewModel(
    private val contactRepository: ContactRepository,
    schedulerProvider: SchedulerProvider
) : BaseViewModel(schedulerProvider) {

    private val _events = SingleLiveEvent<Event>()
    val events: LiveData<Event>
        get() = _events

    fun loadAllContacts() {
        _events.value = LoadingEvent
        launch {
            try {
                contactRepository.getContactList().await()
                _events.value = SuccessEvent
            } catch (error: Throwable) {
                _events.value = FailedEvent(error)
            }
        }
    }
}