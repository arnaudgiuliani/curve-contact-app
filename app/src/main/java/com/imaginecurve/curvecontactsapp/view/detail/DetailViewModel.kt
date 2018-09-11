package com.imaginecurve.curvecontactsapp.view.detail

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.imaginecurve.curvecontactsapp.domain.ContactRepository
import com.imaginecurve.curvecontactsapp.util.coroutines.SchedulerProvider
import com.imaginecurve.curvecontactsapp.util.mvvm.BaseViewModel
import com.imaginecurve.curvecontactsapp.util.mvvm.ErrorState
import com.imaginecurve.curvecontactsapp.util.mvvm.SingleLiveEvent
import com.imaginecurve.curvecontactsapp.util.mvvm.State

class DetailViewModel(
    private val contactRepository: ContactRepository,
    schedulerProvider: SchedulerProvider
) : BaseViewModel(schedulerProvider) {

    private val _states = MutableLiveData<State>()
    val states: LiveData<State>
        get() = _states

    fun getDetail(id: String) {
        launch {
            try {
                val detail = contactRepository.getContactById(id).await()
                if (detail != null) {
                    _states.value = LoadedState(DetailUIModel.from(detail))
                } else {
                    _states.value =
                            ErrorState(IllegalStateException("Can't find contact for id $id"))
                }
            } catch (e: Throwable) {
                _states.value = ErrorState(e)
            }
        }
    }

    data class LoadedState(val detail: DetailUIModel) : State()
}