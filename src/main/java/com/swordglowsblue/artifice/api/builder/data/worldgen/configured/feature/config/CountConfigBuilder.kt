package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.feature.config

import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.data.worldgen.UniformIntDistributionBuilder

class CountConfigBuilder : FeatureConfigBuilder() {
    fun count(count: Int): CountConfigBuilder {
        this.root.addProperty("count", count)
        return this
    }

    fun count(processor: UniformIntDistributionBuilder.() -> Unit): CountConfigBuilder {
        with("count", { JsonObject() }) { jsonObject: JsonObject ->
            UniformIntDistributionBuilder().apply(processor).buildTo(jsonObject)
        }
        return this
    }
}