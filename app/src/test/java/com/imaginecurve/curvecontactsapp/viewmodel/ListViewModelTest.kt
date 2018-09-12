package com.imaginecurve.curvecontactsapp.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.imaginecurve.curvecontactsapp.data.mock.MockedContactDataSource
import com.imaginecurve.curvecontactsapp.domain.ContactRepository
import com.imaginecurve.curvecontactsapp.util.coroutines.TestSchedulerProvider
import com.imaginecurve.curvecontactsapp.util.mockito.MockitoHelper.argumentCaptor
import com.imaginecurve.curvecontactsapp.util.mvvm.ErrorState
import com.imaginecurve.curvecontactsapp.util.mvvm.LoadingState
import com.imaginecurve.curvecontactsapp.util.mvvm.State
import com.imaginecurve.curvecontactsapp.view.list.ItemUIModel
import com.imaginecurve.curvecontactsapp.view.list.ListViewModel
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.AutoCloseKoinTest
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class ListViewModelTest : AutoCloseKoinTest() {

    lateinit var viewModel: ListViewModel
    @Mock
    lateinit var view: Observer<State>
    @Mock
    lateinit var repository: ContactRepository

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)

        viewModel = ListViewModel(repository, TestSchedulerProvider())
        viewModel.states.observeForever(view)
    }

    @After
    fun after() {
        viewModel.onCleared()
    }

    @Test
    fun `ListViewModel got items`() = runBlocking {
        val contacts = MockedContactDataSource().retrieveAllContacts()

        given(repository.getContactList()).willReturn(async { contacts })
        // reuse mocked data

        viewModel.getContacts()

        // setup ArgumentCaptor
        val arg = argumentCaptor<State>()
        // Here we expect 2 calls on view.onChanged
        verify(view, times(2)).onChanged(arg.capture())

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