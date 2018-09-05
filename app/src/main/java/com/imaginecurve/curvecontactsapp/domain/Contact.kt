package com.imaginecurve.curvecontactsapp.domain

import java.util.*

data class Contact(val name : String, val phone : String, val email : String, val id : String = UUID.randomUUID().toString())