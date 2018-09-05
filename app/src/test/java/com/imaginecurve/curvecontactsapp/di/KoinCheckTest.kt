package com.imaginecurve.curvecontactsapp.di

import org.junit.Test
import org.koin.test.AutoCloseKoinTest
import org.koin.test.checkModules

class KoinCheckTest : AutoCloseKoinTest() {

    @Test
    fun `check app & mockedDataModule`() {
        checkModules(listOf(appModule, mockedDataModule))
    }

    @Test
    fun `check appModule, mockedDataModule, testSchedulerModule`() {
        checkModules(listOf(appModule, mockedDataModule, testSchedulerModule))
    }
}