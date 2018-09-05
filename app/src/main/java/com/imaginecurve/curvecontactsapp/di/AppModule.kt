package com.imaginecurve.curvecontactsapp.di

import com.imaginecurve.curvecontactsapp.data.ContactDataSource
import com.imaginecurve.curvecontactsapp.data.mock.MockedContactDataSource
import com.imaginecurve.curvecontactsapp.domain.ContactRepository
import com.imaginecurve.curvecontactsapp.domain.ContactRepositoryImpl
import com.imaginecurve.curvecontactsapp.util.coroutines.ApplicationSchedulerProvider
import com.imaginecurve.curvecontactsapp.util.coroutines.SchedulerProvider
import com.imaginecurve.curvecontactsapp.view.detail.DetailViewModel
import com.imaginecurve.curvecontactsapp.view.list.ListViewModel
import com.imaginecurve.curvecontactsapp.view.splash.SplashViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module {

    viewModel { SplashViewModel(get(), get()) }
    viewModel { ListViewModel(get(), get()) }
    viewModel { DetailViewModel(get(), get()) }

    single<ContactRepository> { ContactRepositoryImpl(get()) }
    single<SchedulerProvider> { ApplicationSchedulerProvider() }
}

val mockedDataModule = module {
    single<ContactDataSource> { MockedContactDataSource() }
}