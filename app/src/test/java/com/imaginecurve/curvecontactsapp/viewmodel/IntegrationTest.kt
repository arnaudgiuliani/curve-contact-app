package com.imaginecurve.curvecontactsapp.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.imaginecurve.curvecontactsapp.di.appModule
import com.imaginecurve.curvecontactsapp.di.mockedDataModule
import com.imaginecurve.curvecontactsapp.di.testSchedulerModule
import com.imaginecurve.curvecontactsapp.view.detail.DetailViewModel
import com.imaginecurve.curvecontactsapp.view.list.ListViewModel
import org.junit.Rule
import org.junit.Test
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.inject
import org.koin.test.AutoCloseKoinTest

class IntegrationTest : AutoCloseKoinTest() {

    val listVM: ListViewModel by inject()
    val detailVM: DetailViewModel by inject()


    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun `load list a get detail`() {
        startKoin(listOf(appModule, mockedDataModule, testSchedulerModule))

        listVM.states.observeForever { state ->
            when (state) {
                is ListViewModel.LoadedState -> {
                    detailVM.getDetail(state.list.first().id)
                }
            }
        }
        detailVM.states.observeForever { state ->
            when (state) {
                is DetailViewModel.LoadedState -> {
                    println("got ${state.detail}")
                }
            }
        }
        listVM.getContacts()
    }

}