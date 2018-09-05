package com.imaginecurve.curvecontactsapp.view.detail

import android.graphics.Bitmap
import com.imaginecurve.curvecontactsapp.domain.Contact

data class DetailUIModel(val name: String, val phone: String, val photo: Bitmap?) {

    companion object {
        fun from(c: Contact) = DetailUIModel(c.name, c.phone, c.photo)
    }
}