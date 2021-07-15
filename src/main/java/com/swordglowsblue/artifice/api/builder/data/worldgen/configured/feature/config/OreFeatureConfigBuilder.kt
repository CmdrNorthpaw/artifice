package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.feature.config

import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.data.RuleTestBuilder
import com.swordglowsblue.artifice.api.builder.data.StateDataBuilder

class OreFeatureConfigBuilder : FeatureConfigBuilder() {
    fun <R : RuleTestBuilder> targetRule(processor: R.() -> Unit, instance: R): OreFeatureConfigBuilder {
        with("target", { JsonObject() }) { jsonObject: JsonObject ->
            instance.apply(processor).buildTo(jsonObject)
        }
        return this
    }

    fun state(processor: StateDataBuilder.() -> Unit): OreFeatureConfigBuilder {
        with("target", { JsonObject() }) { jsonObject: JsonObject ->
            StateDataBuilder().apply(processor).buildTo(jsonObject)
        }
        return this
    }

    fun size(size: Int): OreFeatureConfigBuilder {
        require(size <= 64) { "size can't be higher than 64! Found $size" }
        require(size >= 0) { "size can't be smaller than 0! Found $size" }
        this.root.addProperty("size", size)
        return this
    }
}