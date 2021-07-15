package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.feature.config

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.data.StateDataBuilder

class SimpleBlockFeatureConfigBuilder : FeatureConfigBuilder() {
    fun toPlace(processor: StateDataBuilder.() -> Unit): SimpleBlockFeatureConfigBuilder {
        with("to_place", { JsonObject() }) { jsonObject: JsonObject ->
            StateDataBuilder().apply(processor).buildTo(jsonObject)
        }
        return this
    }

    fun addPlaceOn(processor: StateDataBuilder.() -> Unit): SimpleBlockFeatureConfigBuilder {
        this.root.getAsJsonArray("place_on")
            .add(StateDataBuilder().apply(processor).buildTo(JsonObject()))
        return this
    }

    fun addPlaceIn(processor: StateDataBuilder.() -> Unit): SimpleBlockFeatureConfigBuilder {
        this.root.getAsJsonArray("place_in")
            .add(StateDataBuilder().apply(processor).buildTo(JsonObject()))
        return this
    }

    fun addPlaceUnder(processor: StateDataBuilder.() -> Unit): SimpleBlockFeatureConfigBuilder {
        this.root.getAsJsonArray("place_under")
            .add(StateDataBuilder().apply(processor).buildTo(JsonObject()))
        return this
    }

    init {
        this.root.add("place_on", JsonArray())
        this.root.add("place_in", JsonArray())
        this.root.add("place_under", JsonArray())
    }
}