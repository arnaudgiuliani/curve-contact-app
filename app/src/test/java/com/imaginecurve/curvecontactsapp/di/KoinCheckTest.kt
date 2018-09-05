package com.imaginecurve.curvecontactsapp.di

import android.content.Context
import org.junit.Test
import org.koin.dsl.module.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.checkModules
import org.mockito.Mockito.mock

class KoinCheckTest : AutoCloseKoinTest() {

    val mockContext = module {
        single { mock(Context::class.java) }
    }

    @Test
    fun `check appModule, dataModule `() {
        checkModules(listOf(appModule, dataModule) + mockContext)
    }

    @Test
    fun `check app & mockedDataModule`() {
        checkModules(listOf(appModule, mockedDataModule))
    }

    @Test
    fun `check appModule, mockedDataModule, testSchedulerModule`() {
        checkModules(listOf(appModule, mockedDataModule, testSchedulerModule))
    }
}