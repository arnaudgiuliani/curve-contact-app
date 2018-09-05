package com.imaginecurve.curvecontactsapp.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.imaginecurve.curvecontactsapp.di.appModule
import com.imaginecurve.curvecontactsapp.di.mockedDataModule
import com.imaginecurve.curvecontactsapp.di.testSchedulerModule
import com.imaginecurve.curvecontactsapp.domain.ContactRepository
import com.imaginecurve.curvecontactsapp.util.mockito.MockitoHelper.any
import com.imaginecurve.curvecontactsapp.util.mockito.MockitoHelper.argumentCaptor
import com.imaginecurve.curvecontactsapp.util.mvvm.ErrorState
import com.imaginecurve.curvecontactsapp.util.mvvm.State
import com.imaginecurve.curvecontactsapp.view.detail.DetailUIModel
import com.imaginecurve.curvecontactsapp.view.detail.DetailViewModel
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

class DetailViewModelTest : AutoCloseKoinTest() {

    val viewModel: DetailViewModel by inject()
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
    fun `DetailViewModel got detail`() = runBlocking {
        // set view - VM
        viewModel.states.observeForever(view)

        // reuse mocked data
        val contacts = repository.getContactList().await()

        val contact = contacts.first()
        viewModel.getDetail(contact.id)

        // setup ArgumentCaptor
        val arg = argumentCaptor<State>()
        // Here we expect 2 calls on view.onChanged
        verify(view, Mockito.times(1)).onChanged(arg.capture())

        val values = arg.allValues
        // Test obtained values in order
        assertEquals(1, values.size)
        assertEquals(
            DetailViewModel.LoadedState(DetailUIModel.from(contact)),
            values[0]
        )
    }

    @Test
    fun `DetailViewModel failed to got detail`() = runBlocking {
        // mock it to throw error
        declareMock<ContactRepository>()
        // set view - VM
        viewModel.states.observeForever(view)

        val error = Throwable("Got error")
        given(repository.getContactById(any())).will { throw error }

        viewModel.getDetail("ANY_ID")

        // setup ArgumentCaptor
        val arg = argumentCaptor<State>()
        // Here we expect 2 calls on view.onChanged
        verify(view, times(1)).onChanged(arg.capture())

        val values = arg.allValues
        // Test obtained values in order
        assertEquals(1, values.size)
        assertEquals(ErrorState(error), values[0])
    }
}