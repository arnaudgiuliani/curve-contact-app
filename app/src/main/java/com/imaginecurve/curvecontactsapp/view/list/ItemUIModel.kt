package com.imaginecurve.curvecontactsapp.view.list

import com.imaginecurve.curvecontactsapp.domain.Contact

data class ItemUIModel(val id: String, val name: String) {
    companion object {
        fun from(c: Contact) = ItemUIModel(c.id, c.name)
    }
}