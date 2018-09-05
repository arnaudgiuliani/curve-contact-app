package com.imaginecurve.curvecontactsapp.view.list

import android.graphics.Bitmap
import com.imaginecurve.curvecontactsapp.domain.Contact
import com.imaginecurve.curvecontactsapp.util.android.MAX_COLOR

data class ItemUIModel(val id: String, val name: String, val photoBitmap: Bitmap?) {

    fun getColorIndex(index: Int): Int {
        return index % MAX_COLOR
    }

    companion object {
        fun from(c: Contact): ItemUIModel {
            return ItemUIModel(c.id, c.name, c.photo)
        }
    }
}