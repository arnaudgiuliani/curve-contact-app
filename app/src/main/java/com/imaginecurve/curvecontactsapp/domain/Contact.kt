package com.imaginecurve.curvecontactsapp.domain

import android.graphics.Bitmap
import java.util.*

data class Contact(val name : String, val phone : String, val photo : Bitmap? = null, val id : String = UUID.randomUUID().toString())