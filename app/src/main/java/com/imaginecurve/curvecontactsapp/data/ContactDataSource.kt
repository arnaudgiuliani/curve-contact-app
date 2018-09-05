package com.imaginecurve.curvecontactsapp.data

import android.graphics.Bitmap
import com.imaginecurve.curvecontactsapp.domain.Contact

interface ContactDataSource {

    fun retrieveAllContacts() : List<Contact>

    fun getBitmap(uri : String) : Bitmap?

}