package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.feature

import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder
import com.swordglowsblue.artifice.api.builder.data.worldgen.configured.feature.config.FeatureConfigBuilder
import java.util.function.Function

class ConfiguredSubFeatureBuilder : TypedJsonBuilder<JsonObject>(JsonObject(), { j: JsonObject -> j }) {
    fun featureID(id: String): ConfiguredSubFeatureBuilder {
        this.root.addProperty("type", id)
        return this
    }

    fun <C : FeatureConfigBuilder> featureConfig(instance: C, processor: C.() -> Unit): ConfiguredSubFeatureBuilder {
        with("config", { JsonObject() }) { jsonObject: JsonObject ->
            instance.apply(processor).buildTo(jsonObject)
        }
        return this
    }

    fun defaultConfig(): ConfiguredSubFeatureBuilder {
        return featureConfig(FeatureConfigBuilder()) {}
    }
}