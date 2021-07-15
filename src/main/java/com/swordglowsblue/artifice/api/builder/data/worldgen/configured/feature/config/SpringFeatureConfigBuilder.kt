package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.feature.config

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.data.StateDataBuilder

class SpringFeatureConfigBuilder : FeatureConfigBuilder() {
    fun fluidState(processor: StateDataBuilder.() -> Unit): SpringFeatureConfigBuilder {
        with("state", { JsonObject() }) { jsonObject: JsonObject ->
            StateDataBuilder().apply(processor).buildTo(jsonObject)
        }
        return this
    }

    fun addValidBlock(blockID: String): SpringFeatureConfigBuilder {
        this.root.getAsJsonArray("valid_blocks").add(blockID)
        return this
    }

    fun requiresBlockBelow(requiresBlockBelow: Boolean): SpringFeatureConfigBuilder {
        this.root.addProperty("requires_block_below", requiresBlockBelow)
        return this
    }

    fun rockCount(rockCount: Int): SpringFeatureConfigBuilder {
        this.root.addProperty("rock_count", rockCount)
        return this
    }

    fun holeCount(holeCount: Int): SpringFeatureConfigBuilder {
        this.root.addProperty("hole_count", holeCount)
        return this
    }

    init {
        this.root.add("valid_blocks", JsonArray())
    }
}