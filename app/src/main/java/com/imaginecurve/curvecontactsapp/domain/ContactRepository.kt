package com.imaginecurve.curvecontactsapp.domain

import com.imaginecurve.curvecontactsapp.data.ContactDataSource
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async

interface ContactRepository {
    fun getContactList(): Deferred<List<Contact>>
    fun getContactById(id: String): Deferred<Contact?>
}

class ContactRepositoryImpl(val contactDataSource: ContactDataSource) : ContactRepository {

    private var _contacts: List<Contact> = ArrayList()

    override fun getContactList(): Deferred<List<Contact>> = async {
        if (_contacts.isEmpty()) {
            _contacts = contactDataSource.retrieveAllContacts()
        }
        _contacts
    }

    override fun getContactById(id: String): Deferred<Contact?> =
        async { _contacts.firstOrNull { it.id == id } }

}