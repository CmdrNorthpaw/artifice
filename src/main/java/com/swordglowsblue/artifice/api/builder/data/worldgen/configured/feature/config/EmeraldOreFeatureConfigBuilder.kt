package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.feature.config

import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.data.StateDataBuilder

class EmeraldOreFeatureConfigBuilder : FeatureConfigBuilder() {
    fun state(processor: StateDataBuilder.() -> Unit): EmeraldOreFeatureConfigBuilder {
        with("state", { JsonObject() }) { jsonObject: JsonObject ->
            StateDataBuilder().apply(processor).buildTo(jsonObject)
        }
        return this
    }

    fun target(processor: StateDataBuilder.() -> Unit): EmeraldOreFeatureConfigBuilder {
        with("target", { JsonObject() }) { jsonObject: JsonObject ->
            StateDataBuilder().apply(processor).buildTo(jsonObject)
        }
        return this
    }
}