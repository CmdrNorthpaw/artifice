package com.swordglowsblue.artifice.api.util

import java.lang.FunctionalInterface
import java.util.function.Consumer

/** A wrapper around [Consumer] that returns its argument instead of `void`.
 * @param <T> The type to be passed in and returned.
</T> */
@Deprecated(message = "Please use a Kotlin lambda instead")
@FunctionalInterface
fun interface Processor<T> : Consumer<T> {
    fun process(t: T): T {
        accept(t)
        return t
    }
}

fun <T> T.process(function: T.() -> Unit): T {
    function(this)
    return this
}