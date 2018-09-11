package com.imaginecurve.curvecontactsapp.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.imaginecurve.curvecontactsapp.data.mock.MockedContactDataSource
import com.imaginecurve.curvecontactsapp.domain.ContactRepository
import com.imaginecurve.curvecontactsapp.util.coroutines.TestSchedulerProvider
import com.imaginecurve.curvecontactsapp.util.mockito.MockitoHelper.any
import com.imaginecurve.curvecontactsapp.util.mockito.MockitoHelper.argumentCaptor
import com.imaginecurve.curvecontactsapp.util.mvvm.ErrorState
import com.imaginecurve.curvecontactsapp.util.mvvm.State
import com.imaginecurve.curvecontactsapp.view.detail.DetailUIModel
import com.imaginecurve.curvecontactsapp.view.detail.DetailViewModel
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

class DetailViewModelTest {

    lateinit var viewModel: DetailViewModel
    @Mock
    lateinit var view: Observer<State>
    @Mock
    lateinit var repository: ContactRepository

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)

        viewModel = DetailViewModel(repository, TestSchedulerProvider())
        viewModel.states.observeForever(view)
    }

    @After
    fun after() {
        viewModel.onCleared()
    }

    @Test
    fun `DetailViewModel got detail`() = runBlocking {
        // set view - VM
        viewModel.states.observeForever(view)

        // reuse mocked data
        val contacts = MockedContactDataSource().retrieveAllContacts()
        val contact = contacts.first()
        given(repository.getContactById(any())).will { async { contact } }

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