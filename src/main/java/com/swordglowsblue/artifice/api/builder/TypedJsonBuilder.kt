package com.swordglowsblue.artifice.api.builder

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.util.Processor
import java.util.function.Consumer
import java.util.function.Function

abstract class TypedJsonBuilder<T>(
    @JvmField protected val root: JsonObject,
    private val ctor: Function<JsonObject?, T>
)  {

    fun build(): T {
        return buildTo(JsonObject())
    }

    fun buildTo(target: JsonObject?): T {
        root.entrySet().forEach(Consumer { (key, value) ->
            target?.add(
                key, value
            )
        })
        return ctor.apply(target)
    }

    protected fun <J : JsonElement?> with(`in`: JsonObject?, key: String?, ctor: () -> J, run: (J) -> Unit) {
        val toProcess = if (`in`?.has(key) == true) `in`.get(key) as J else ctor()
        `in`?.add(key, toProcess.also(run))
    }

    fun <J : JsonElement?> with(key: String?, ctor: () -> J, run: (J) -> Unit) {
        this.with(root, key, ctor, run)
    }

    fun jsonElement(name: String?, value: JsonElement?): TypedJsonBuilder<T> {
        root.add(name, value)
        return this
    }

    fun jsonString(name: String?, value: String?): TypedJsonBuilder<T> {
        root.addProperty(name, value)
        return this
    }

    fun jsonBoolean(name: String?, value: Boolean): TypedJsonBuilder<T> {
        root.addProperty(name, value)
        return this
    }

    fun jsonNumber(name: String?, value: Number?): TypedJsonBuilder<T> {
        root.addProperty(name, value)
        return this
    }

    fun jsonChar(name: String?, value: Char?): TypedJsonBuilder<T> {
        root.addProperty(name, value)
        return this
    }

    @Deprecated("Please use the lambda version instead")
    @SuppressWarnings("DEPRECATED")
    fun jsonArray(name: String?, settings: Processor<JsonArrayBuilder>): TypedJsonBuilder<T> {
        root.add(name, settings.process(JsonArrayBuilder()).build())
        return this
    }

    fun jsonArray(name: String, settings: JsonArrayBuilder.() -> Unit): TypedJsonBuilder<T> {
        root.add(name, JsonArrayBuilder().apply(settings).build())
        return this
    }

    protected fun arrayOf(vararg values: Boolean): JsonArray {
        val array = JsonArray()
        for (i in values) array.add(i)
        return array
    }

    protected fun arrayOf(vararg values: Char?): JsonArray {
        val array = JsonArray()
        for (i in values) array.add(i)
        return array
    }

    protected fun arrayOf(vararg values: Number?): JsonArray {
        val array = JsonArray()
        for (i in values) array.add(i)
        return array
    }

    protected fun arrayOf(vararg values: String?): JsonArray {
        val array = JsonArray()
        for (i in values) array.add(i)
        return array
    }
}