package com.swordglowsblue.artifice.api.util

import java.util.function.Consumer

/** A wrapper around [Consumer] that returns its argument instead of `void`.
 * @param <T> The type to be passed in and returned.
</T> */
@Deprecated(message = "Please use a Kotlin lambda instead", replaceWith = ReplaceWith("Builder<T>"))
@FunctionalInterface
fun interface Processor<T> : Consumer<T> {
    fun process(t: T): T {
        accept(t)
        return t
    }
}