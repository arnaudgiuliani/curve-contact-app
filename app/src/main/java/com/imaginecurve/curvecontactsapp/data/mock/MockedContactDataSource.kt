package com.imaginecurve.curvecontactsapp.data.mock

import android.graphics.Bitmap
import com.imaginecurve.curvecontactsapp.data.ContactDataSource
import com.imaginecurve.curvecontactsapp.domain.Contact

class MockedContactDataSource : ContactDataSource {

    private val _contacts = listOf(
        Contact("User1","0123456789"),
        Contact("User2","0123456789"),
        Contact("User3","0123456789"),
        Contact("User4","0123456789"),
        Contact("User5","0123456789"),
        Contact("User6","0123456789"),
        Contact("User7","0123456789")
    )

    override fun retrieveAllContacts(): List<Contact>  = _contacts

    override fun getBitmap(uri: String): Bitmap? = null
}
