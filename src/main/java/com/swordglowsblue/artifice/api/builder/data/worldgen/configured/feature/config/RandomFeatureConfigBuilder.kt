package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.feature.config

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder
import java.util.function.Function

class RandomFeatureConfigBuilder : FeatureConfigBuilder() {
    fun defaultConfiguredFeature(configuredFeatureID: String): RandomFeatureConfigBuilder {
        this.root.addProperty("default", configuredFeatureID)
        return this
    }

    fun addConfiguredFeature(processor: RandomFeatureEntryBuilder.() -> Unit): RandomFeatureConfigBuilder {
        this.root.getAsJsonArray("features")
            .add(RandomFeatureEntryBuilder().apply(processor).buildTo(JsonObject()))
        return this
    }

    class RandomFeatureEntryBuilder : TypedJsonBuilder<JsonObject>(JsonObject(), { j: JsonObject -> j }) {
        fun chance(chance: Float): RandomFeatureEntryBuilder {
            require(chance <= 1.0f) { "chance can't be higher than 1.0F! Found $chance" }
            require(chance >= 0.0f) { "chance can't be smaller than 0.0F! Found $chance" }
            this.root.addProperty("chance", chance)
            return this
        }

        fun configuredFeatureID(configuredFeatureID: String): RandomFeatureEntryBuilder {
            this.root.addProperty("feature", configuredFeatureID)
            return this
        }
    }

    init {
        this.root.add("features", JsonArray())
    }
}