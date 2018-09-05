package com.imaginecurve.curvecontactsapp.domain

import com.imaginecurve.curvecontactsapp.data.ContactDataSource

interface ContactRepository {
    fun getContactList(): List<Contact>
    fun getContactById(id: String): Contact?
}

class ContactRepositoryImpl(val contactDataSource: ContactDataSource) : ContactRepository {

    private var _contacts: List<Contact> = ArrayList()

    override fun getContactList(): List<Contact> {
        if (_contacts.isEmpty()) {
            _contacts = contactDataSource.retrieveAllContacts()
        }
        return _contacts
    }

    override fun getContactById(id: String): Contact? = _contacts.firstOrNull { it.id == id }

}