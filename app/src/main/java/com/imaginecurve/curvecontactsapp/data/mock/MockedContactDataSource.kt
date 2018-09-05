package com.imaginecurve.curvecontactsapp.data.mock

import com.imaginecurve.curvecontactsapp.data.ContactDataSource
import com.imaginecurve.curvecontactsapp.domain.Contact

class MockedContactDataSource : ContactDataSource {

    private val _contacts = listOf(
        Contact("User1","0123456789","user1@email.com"),
        Contact("User2","0123456789","user2@email.com"),
        Contact("User3","0123456789","user3@email.com"),
        Contact("User4","0123456789","user4@email.com"),
        Contact("User5","0123456789","user5@email.com"),
        Contact("User6","0123456789","user6@email.com"),
        Contact("User7","0123456789","user7@email.com")
    )

    override fun retrieveAllContacts(): List<Contact>  = _contacts
}