package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.feature

import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder
import com.swordglowsblue.artifice.api.builder.data.worldgen.configured.feature.config.FeatureConfigBuilder
import com.swordglowsblue.artifice.api.resource.JsonResource
import com.swordglowsblue.artifice.api.util.Processor
import java.util.function.Function

class ConfiguredFeatureBuilder : TypedJsonBuilder<JsonResource<JsonObject>>(
    JsonObject(),
    Function<JsonObject, JsonResource<JsonObject>> { root: JsonObject -> JsonResource(root) }) {
    fun featureID(id: String?): ConfiguredFeatureBuilder {
        this.root.addProperty("type", id)
        return this
    }

    fun <C : FeatureConfigBuilder?> featureConfig(processor: Processor<C>, instance: C): ConfiguredFeatureBuilder {
        with("config", { JsonObject() }) { jsonObject: JsonObject? ->
            processor.process(instance)!!
                .buildTo(jsonObject!!)
        }
        return this
    }

    fun defaultConfig(): ConfiguredFeatureBuilder {
        return featureConfig({ featureConfigBuilder: FeatureConfigBuilder? -> }, FeatureConfigBuilder())
    }
}