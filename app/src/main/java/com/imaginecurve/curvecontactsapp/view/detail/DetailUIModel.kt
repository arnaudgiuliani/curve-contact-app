package com.imaginecurve.curvecontactsapp.view.detail

import com.imaginecurve.curvecontactsapp.domain.Contact

data class DetailUIModel(val name: String, val phone: String, val email: String) {

    companion object {
        fun from(c: Contact) = DetailUIModel(c.name, c.phone, c.email)
    }
}