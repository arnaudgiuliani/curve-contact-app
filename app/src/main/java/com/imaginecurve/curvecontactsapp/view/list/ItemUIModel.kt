package com.imaginecurve.curvecontactsapp.view.list

import com.imaginecurve.curvecontactsapp.domain.Contact
import com.imaginecurve.curvecontactsapp.util.android.MAX_COLOR

data class ItemUIModel(val id: String, val name: String) {

    fun getColorIndex(index: Int): Int {
        return index % MAX_COLOR
    }

    companion object {
        fun from(c: Contact) = ItemUIModel(c.id, c.name)
    }
}