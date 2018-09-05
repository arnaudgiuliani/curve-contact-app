package com.imaginecurve.curvecontactsapp.data

import com.imaginecurve.curvecontactsapp.domain.Contact

class MockedContactDataSource : ContactDataSource {

    private val _contacts = listOf(
        Contact("User1","0123456789","user1@email.com"),
        Contact("User2","0123456789","user2@email.com"),
        Contact("User3","0123456789","user3@email.com"),
        Contact("User4","0123456789","user4@email.com")
    )

    override fun retrieveAllContacts(): List<Contact>  = _contacts
}