package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.feature.config

import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.data.StateDataBuilder

class FillLayerFeatureConfigBuilder : FeatureConfigBuilder() {
    fun state(processor: StateDataBuilder.() -> Unit): FillLayerFeatureConfigBuilder {
        with("state", { JsonObject() }) { jsonObject: JsonObject ->
            StateDataBuilder().apply(processor).buildTo(jsonObject)
        }
        return this
    }

    fun height(height: Int): FillLayerFeatureConfigBuilder {
        require(height <= 255) { "height can't be higher than 255! Found $height" }
        require(height >= 0) { "height can't be smaller than 0! Found $height" }
        this.root.addProperty("height", height)
        return this
    }
}