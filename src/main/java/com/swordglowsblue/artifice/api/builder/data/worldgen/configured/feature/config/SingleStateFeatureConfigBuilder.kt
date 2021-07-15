package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.feature.config

import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.data.StateDataBuilder

class SingleStateFeatureConfigBuilder : FeatureConfigBuilder() {
    fun state(processor: StateDataBuilder.() -> Unit): SingleStateFeatureConfigBuilder {
        with("state", { JsonObject() }) { jsonObject: JsonObject ->
            StateDataBuilder().apply(processor).buildTo(jsonObject)
        }
        return this
    }
}