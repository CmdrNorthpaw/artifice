package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.feature.config

import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.data.StateDataBuilder
import com.swordglowsblue.artifice.api.builder.data.worldgen.UniformIntDistributionBuilder

class NetherrackReplaceBlobsFeatureConfigBuilder : FeatureConfigBuilder() {
    fun radius(processor: UniformIntDistributionBuilder.() -> Unit): NetherrackReplaceBlobsFeatureConfigBuilder {
        with("radius", { JsonObject() }) { jsonObject: JsonObject ->
            UniformIntDistributionBuilder().apply(processor).buildTo(jsonObject)
        }
        return this
    }

    fun target(processor: StateDataBuilder.() -> Unit): NetherrackReplaceBlobsFeatureConfigBuilder {
        with("target", { JsonObject() }) { jsonObject: JsonObject ->
            StateDataBuilder().apply(processor).buildTo(jsonObject)
        }
        return this
    }

    fun state(processor: StateDataBuilder.() -> Unit): NetherrackReplaceBlobsFeatureConfigBuilder {
        with("state", { JsonObject() }) { jsonObject: JsonObject ->
            StateDataBuilder().apply(processor).buildTo(jsonObject)
        }
        return this
    }
}