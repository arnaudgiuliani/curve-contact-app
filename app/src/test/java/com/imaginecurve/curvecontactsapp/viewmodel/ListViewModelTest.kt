package com.imaginecurve.curvecontactsapp.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.imaginecurve.curvecontactsapp.di.appModule
import com.imaginecurve.curvecontactsapp.di.mockedDataModule
import com.imaginecurve.curvecontactsapp.di.testSchedulerModule
import com.imaginecurve.curvecontactsapp.domain.ContactRepository
import com.imaginecurve.curvecontactsapp.util.mockito.MockitoHelper.argumentCaptor
import com.imaginecurve.curvecontactsapp.util.mvvm.ErrorState
import com.imaginecurve.curvecontactsapp.util.mvvm.LoadingState
import com.imaginecurve.curvecontactsapp.util.mvvm.State
import com.imaginecurve.curvecontactsapp.view.list.ItemUIModel
import com.imaginecurve.curvecontactsapp.view.list.ListViewModel
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.inject
import org.koin.test.AutoCloseKoinTest
import org.koin.test.declareMock
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

class ListViewModelTest : AutoCloseKoinTest() {

    val viewModel: ListViewModel by inject()
    val view: Observer<State> by inject()
    val repository: ContactRepository by inject()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun before() {
        startKoin(listOf(appModule, mockedDataModule, testSchedulerModule))

        // Mock View
        declareMock<Observer<State>>()
    }

    @Test
    fun `ListViewModel got items`() = runBlocking {
        // set view - VM
        viewModel.states.observeForever(view)

        // reuse mocked data
        val contacts = repository.getContactList().await()

        viewModel.getContacts()

        // setup ArgumentCaptor
        val arg = argumentCaptor<State>()
        // Here we expect 2 calls on view.onChanged
        verify(view, Mockito.times(2)).onChanged(arg.capture())

        val values = arg.allValues
        // Test obtained values in order
        assertEquals(2, values.size)
        assertEquals(LoadingState, values[0])
        assertEquals(
            ListViewModel.LoadedState(contacts.map { ItemUIModel.from(it) }),
            values[1]
        )
    }

    @Test
    fun `ListViewModel failed to got items`() = runBlocking {
        // mock it to throw error
        declareMock<ContactRepository>()
        // set view - VM
        viewModel.states.observeForever(view)

        val error = Throwable("Got error")
        given(repository.getContactList()).will { throw error }

        viewModel.getContacts()

        // setup ArgumentCaptor
        val arg = argumentCaptor<State>()
        // Here we expect 2 calls on view.onChanged
        verify(view, times(2)).onChanged(arg.capture())

        val values = arg.allValues
        // Test obtained values in order
        assertEquals(2, values.size)
        assertEquals(LoadingState, values[0])
        assertEquals(ErrorState(error), values[1])
    }
}