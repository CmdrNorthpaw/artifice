package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.feature.config

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.data.StateDataBuilder
import com.swordglowsblue.artifice.api.builder.data.worldgen.UniformIntDistributionBuilder

class DiskFeatureConfigBuilder : FeatureConfigBuilder() {
    fun state(processor: StateDataBuilder.() -> Unit): DiskFeatureConfigBuilder {
        with("state", { JsonObject() }) { jsonObject: JsonObject ->
            StateDataBuilder().apply(processor).buildTo(jsonObject)
        }
        return this
    }

    fun radius(radius: Int): DiskFeatureConfigBuilder {
        this.root.addProperty("radius", radius)
        return this
    }

    fun radius(processor: UniformIntDistributionBuilder.() -> Unit): DiskFeatureConfigBuilder {
        with("radius", { JsonObject() }) { jsonObject: JsonObject ->
            UniformIntDistributionBuilder().apply(processor).buildTo(jsonObject)
        }
        return this
    }

    fun halfHeight(halfHeight: Int): DiskFeatureConfigBuilder {
        require(halfHeight <= 4) { "halfHeight can't be higher than 4! Found $halfHeight" }
        require(halfHeight >= 0) { "halfHeight can't be smaller than 0! Found $halfHeight" }
        this.root.addProperty("half_height", halfHeight)
        return this
    }

    fun addTarget(processor: StateDataBuilder.() -> Unit): DiskFeatureConfigBuilder {
        this.root.getAsJsonArray("targets").add(StateDataBuilder().apply(processor).buildTo(JsonObject()))
        return this
    }

    init {
        this.root.add("targets", JsonArray())
    }
}