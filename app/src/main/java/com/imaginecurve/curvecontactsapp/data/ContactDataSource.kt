package com.imaginecurve.curvecontactsapp.data

import com.imaginecurve.curvecontactsapp.domain.Contact

interface ContactDataSource {

    fun retrieveAllContacts() : List<Contact>

}