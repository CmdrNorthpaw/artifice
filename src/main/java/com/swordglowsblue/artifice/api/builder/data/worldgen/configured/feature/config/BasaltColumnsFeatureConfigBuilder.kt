package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.feature.config

import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.data.worldgen.UniformIntDistributionBuilder

class BasaltColumnsFeatureConfigBuilder : FeatureConfigBuilder() {
    fun reach(processor: UniformIntDistributionBuilder.() -> Unit): BasaltColumnsFeatureConfigBuilder {
        with("reach", { JsonObject() }) { jsonObject: JsonObject ->
            UniformIntDistributionBuilder().apply(processor).buildTo(jsonObject)
        }
        return this
    }

    fun height(processor: UniformIntDistributionBuilder.() -> Unit): BasaltColumnsFeatureConfigBuilder {
        with("height", { JsonObject() }) { jsonObject: JsonObject ->
            UniformIntDistributionBuilder().apply(processor).buildTo(jsonObject)
        }
        return this
    }
}