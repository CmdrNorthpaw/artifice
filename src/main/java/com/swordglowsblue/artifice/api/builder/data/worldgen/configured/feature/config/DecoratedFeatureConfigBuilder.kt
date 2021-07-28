package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.feature.config

import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.data.worldgen.configured.decorator.ConfiguredDecoratorBuilder

class DecoratedFeatureConfigBuilder : FeatureConfigBuilder() {
    fun feature(processor: ConfiguredDecoratorBuilder.() -> Unit): DecoratedFeatureConfigBuilder {
        with("feature", { JsonObject() }) { jsonObject: JsonObject ->
            ConfiguredDecoratorBuilder().apply(processor).buildTo(jsonObject)
        }
        return this
    }

    fun feature(configuredFeatureID: String): DecoratedFeatureConfigBuilder {
        this.root.addProperty("feature", configuredFeatureID)
        return this
    }

    fun decorator(processor: ConfiguredDecoratorBuilder.() -> Unit): DecoratedFeatureConfigBuilder {
        with("decorator", { JsonObject() }) { jsonObject: JsonObject ->
            ConfiguredDecoratorBuilder().apply(processor).buildTo(jsonObject)
        }
        return this
    }

    fun decorator(configuredDecoratorID: String): DecoratedFeatureConfigBuilder {
        this.root.addProperty("decorator", configuredDecoratorID)
        return this
    }
}