package com.imaginecurve.curvecontactsapp.util.mockito

import org.mockito.ArgumentCaptor
import org.mockito.Mockito

object MockitoHelper {

    /**
     * Returns Mockito.any() as nullable type to avoid java.lang.IllegalStateException when
     * null is returned.
     */
    fun <T> any(): T = Mockito.any<T>()


    /**
     * Helper function for creating an argumentCaptor in kotlin.
     */
    inline fun <reified T : Any> argumentCaptor(): ArgumentCaptor<T> =
        ArgumentCaptor.forClass(T::class.java)
}