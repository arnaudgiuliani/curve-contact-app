package com.imaginecurve.curvecontactsapp.data.android

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.ContactsContract
import android.provider.MediaStore
import com.imaginecurve.curvecontactsapp.data.ContactDataSource
import com.imaginecurve.curvecontactsapp.domain.Contact

class AndroidContactDataSource(val context: Context) : ContactDataSource {

    override fun retrieveAllContacts(): List<Contact> {

        val cursor = context.contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        val count: Int = cursor.count
        val list = (0..(count - 1)).mapNotNull {
            cursor.moveToPosition(it)

            val name = cursor.getString(20)
            val defNbr = cursor.getString(4)
            val photoUri = cursor.getString(18)

            photoUri?.let { uri ->
                println("$name -> $uri")
            }

            if (defNbr != null && defNbr.isNotEmpty()) {
                Contact(name, defNbr, photoUri?.let { uri -> getBitmap(uri) })
            } else null
        }
        cursor.close()
        return list
    }

    override fun getBitmap(uri: String): Bitmap? {
        return MediaStore.Images.Media.getBitmap(context.contentResolver, Uri.parse(uri));
    }
}