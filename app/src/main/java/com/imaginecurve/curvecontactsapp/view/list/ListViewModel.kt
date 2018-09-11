package com.imaginecurve.curvecontactsapp.view.list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.imaginecurve.curvecontactsapp.domain.ContactRepository
import com.imaginecurve.curvecontactsapp.util.coroutines.SchedulerProvider
import com.imaginecurve.curvecontactsapp.util.mvvm.*

class ListViewModel(
    private val contactRepository: ContactRepository,
    schedulerProvider: SchedulerProvider
) : BaseViewModel(schedulerProvider) {

    private val _states = MutableLiveData<State>()
    val states: LiveData<State>
        get() = _states

    fun getContacts() {
        launch {
            _states.value = LoadingState
            try {
                val items = contactRepository.getContactList().await()
                    .map {
                        ItemUIModel.from(it)
                    }
                _states.value = LoadedState(items)
            } catch (e: Throwable) {
                _states.value = ErrorState(e)
            }
        }
    }

    data class LoadedState(val list: List<ItemUIModel>) : State()
}