package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.decorator.config

import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.data.worldgen.configured.decorator.ConfiguredDecoratorBuilder

class DecoratedDecoratorConfigBuilder : DecoratorConfigBuilder() {
    fun outerDecorator(processor: ConfiguredDecoratorBuilder.() -> Unit): DecoratedDecoratorConfigBuilder {
        with("outer", { JsonObject() }) { jsonObject: JsonObject? ->
            ConfiguredDecoratorBuilder().apply(processor).buildTo(jsonObject)
        }
        return this
    }

    fun innerDecorator(processor: ConfiguredDecoratorBuilder.() -> Unit): DecoratedDecoratorConfigBuilder {
        with("inner", { JsonObject() }) { jsonObject: JsonObject? ->
            ConfiguredDecoratorBuilder().apply(processor).buildTo(jsonObject)
        }
        return this
    }
}