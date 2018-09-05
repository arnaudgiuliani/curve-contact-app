@file:Suppress("UNCHECKED_CAST")

package com.imaginecurve.curvecontactsapp.util.ext.android

import android.support.v7.app.AppCompatActivity

/**
 * Retrieve argument from Activity intent
 */
fun <T : Any> AppCompatActivity.argument(key: String) =
    lazy { intent.extras?.get(key) as? T ?: error("Intent Argument $key is missing") }