package com.swordglowsblue.artifice.api.builder

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.util.function.Function

open class JsonObjectBuilder : TypedJsonBuilder<JsonObject?> {
    constructor() : super(JsonObject(), Function<JsonObject?, JsonObject?> { j: JsonObject? -> j }) {}
    constructor(root: JsonObject) : super(root, Function<JsonObject?, JsonObject?> { j: JsonObject? -> j }) {}

    fun add(name: String, value: JsonElement): JsonObjectBuilder {
        root.add(name, value)
        return this
    }

    fun add(name: String, value: String): JsonObjectBuilder {
        root.addProperty(name, value)
        return this
    }

    fun add(name: String, value: Boolean): JsonObjectBuilder {
        root.addProperty(name, value)
        return this
    }

    fun add(name: String, value: Number): JsonObjectBuilder {
        root.addProperty(name, value)
        return this
    }

    fun add(name: String, value: Char): JsonObjectBuilder {
        root.addProperty(name, value)
        return this
    }

    fun addObject(name: String, settings: JsonObjectBuilder.() -> Unit): JsonObjectBuilder {
        root.add(name, JsonObjectBuilder().apply(settings).build())
        return this
    }

    fun addArray(name: String, settings: JsonArrayBuilder.() -> Unit): JsonObjectBuilder {
        root.add(name, JsonArrayBuilder().apply(settings).build())
        return this
    }
}