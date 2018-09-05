package com.imaginecurve.curvecontactsapp.repository

import com.imaginecurve.curvecontactsapp.data.ContactDataSource
import com.imaginecurve.curvecontactsapp.di.appModule
import com.imaginecurve.curvecontactsapp.di.mockedDataModule
import com.imaginecurve.curvecontactsapp.domain.ContactRepository
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.inject
import org.koin.test.AutoCloseKoinTest

class ContactRepositoryTest : AutoCloseKoinTest() {

    val contactRepository: ContactRepository by inject()
    val dataSource: ContactDataSource by inject()

    @Before
    fun before() {
        startKoin(listOf(appModule, mockedDataModule))
    }

    @Test
    fun `should get all contacts`() = runBlocking {
        val list = contactRepository.getContactList().await()
        assertEquals(dataSource.retrieveAllContacts().size, list.size)
    }

    @Test
    fun `should get contact by id`() = runBlocking {
        val allContacts = dataSource.retrieveAllContacts()
        contactRepository.getContactList().await()
        val first = allContacts.first()
        val test = contactRepository.getContactById(first.id).await()
        assertEquals(first, test)
    }

    @Test
    fun `should not get contact by id`() = runBlocking {
        contactRepository.getContactList().await()
        val test = contactRepository.getContactById("NO_ID").await()
        assertNull(test)
    }
}