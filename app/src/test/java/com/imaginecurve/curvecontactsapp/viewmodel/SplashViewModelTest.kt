package com.imaginecurve.curvecontactsapp.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.imaginecurve.curvecontactsapp.domain.Contact
import com.imaginecurve.curvecontactsapp.domain.ContactRepository
import com.imaginecurve.curvecontactsapp.util.coroutines.TestSchedulerProvider
import com.imaginecurve.curvecontactsapp.util.mockito.MockitoHelper.argumentCaptor
import com.imaginecurve.curvecontactsapp.util.mvvm.Event
import com.imaginecurve.curvecontactsapp.util.mvvm.FailedEvent
import com.imaginecurve.curvecontactsapp.util.mvvm.LoadingEvent
import com.imaginecurve.curvecontactsapp.util.mvvm.SuccessEvent
import com.imaginecurve.curvecontactsapp.view.splash.SplashViewModel
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class SplashViewModelTest {

    lateinit var viewModel: SplashViewModel
    @Mock
    lateinit var view: Observer<Event>
    @Mock
    lateinit var repository: ContactRepository

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)

        viewModel = SplashViewModel(repository, TestSchedulerProvider())
        viewModel.events.observeForever(view)
    }

    @After
    fun after(){
        viewModel.onCleared()
    }

    @Test
    fun `SplashViewModel is loading`() = runBlocking {
        val contacts = listOf(Mockito.mock(Contact::class.java))

        given(repository.getContactList()).willReturn(async { contacts })

        viewModel.loadAllContacts()

        // setup ArgumentCaptor
        val arg = argumentCaptor<Event>()
        // Here we expect 2 calls on view.onChanged
        verify(view, times(2)).onChanged(arg.capture())

        val values = arg.allValues
        // Test obtained values in order
        assertEquals(2, values.size)
        assertEquals(LoadingEvent, values[0])
        assertEquals(SuccessEvent, values[1])
    }

    @Test
    fun `SplashViewModel is not loading`() = runBlocking {

        val error = Throwable("Got error")
        given(repository.getContactList()).will { throw error }

        viewModel.loadAllContacts()

        // setup ArgumentCaptor
        val arg = argumentCaptor<Event>()
        // Here we expect 2 calls on view.onChanged
        verify(view, times(2)).onChanged(arg.capture())

        val values = arg.allValues
        // Test obtained values in order
        assertEquals(2, values.size)
        assertEquals(LoadingEvent, values[0])
        assertEquals(FailedEvent(error), values[1])
    }
}