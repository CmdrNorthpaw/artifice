package com.swordglowsblue.artifice.api.builder.data

import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder
import java.util.function.Function

class StateDataBuilder : TypedJsonBuilder<JsonObject?>(JsonObject(), Function { j: JsonObject? -> j }) {
    private val jsonObject = JsonObject()

    /**
     * Set the id of the block.
     * @param id
     * @return
     */
    fun name(id: String?): StateDataBuilder {
        this.root.addProperty("Name", id)
        return this
    }

    /**
     * Set a property to a state.
     * @param property
     * @param state
     * @return
     */
    fun setProperty(property: String?, state: String?): StateDataBuilder {
        jsonObject.addProperty(property, state)
        this.root.add("Properties", jsonObject)
        return this
    }
}