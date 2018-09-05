package com.imaginecurve.curvecontactsapp.util.android

import android.content.Context
import com.imaginecurve.curvecontactsapp.R

fun getColor(index: Int, ctx: Context): Int {
    val colorRes = when (index) {
        0 -> R.color.col_0
        1 -> R.color.col_1
        2 -> R.color.col_2
        3 -> R.color.col_3
        4 -> R.color.col_4
        else -> error("shouldn't get color for index $index")
    }
    return ctx.resources.getColor(colorRes)
}

const val MAX_COLOR = 5



