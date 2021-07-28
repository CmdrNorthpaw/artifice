package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.feature.config

import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.data.StateDataBuilder
import com.swordglowsblue.artifice.api.builder.data.worldgen.UniformIntDistributionBuilder

class DeltaFeatureConfigBuilder : FeatureConfigBuilder() {
    fun size(processor: UniformIntDistributionBuilder.() -> Unit): DeltaFeatureConfigBuilder {
        with("size", { JsonObject() }) { jsonObject: JsonObject ->
            UniformIntDistributionBuilder().apply(processor).buildTo(jsonObject)
        }
        return this
    }

    fun rimSize(processor: UniformIntDistributionBuilder.() -> Unit): DeltaFeatureConfigBuilder {
        with("rim_size", { JsonObject() }) { jsonObject: JsonObject ->
            UniformIntDistributionBuilder().apply(processor).buildTo(jsonObject)
        }
        return this
    }

    fun rim(processor: StateDataBuilder.() -> Unit): DeltaFeatureConfigBuilder {
        with("rim", { JsonObject() }) { jsonObject: JsonObject ->
            StateDataBuilder().apply(processor).buildTo(jsonObject)
        }
        return this
    }

    fun contents(processor: StateDataBuilder.() -> Unit): DeltaFeatureConfigBuilder {
        with("contents", { JsonObject() }) { jsonObject: JsonObject ->
            StateDataBuilder().apply(processor).buildTo(jsonObject)
        }
        return this
    }
}