package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.decorator

import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder
import com.swordglowsblue.artifice.api.builder.data.worldgen.configured.decorator.config.DecoratorConfigBuilder
import java.util.function.Function

class ConfiguredDecoratorBuilder : TypedJsonBuilder<JsonObject?>(JsonObject(), Function { j: JsonObject? -> j }) {
    fun name(decoratorID: String): ConfiguredDecoratorBuilder {
        this.root.addProperty("type", decoratorID)
        return this
    }

    fun <C : DecoratorConfigBuilder> config(instance: C, processor: C.() -> Unit): ConfiguredDecoratorBuilder {
        with("config", { JsonObject() }) { jsonObject: JsonObject? ->
            instance.apply(processor).buildTo(jsonObject)
        }
        return this
    }

    fun defaultConfig(): ConfiguredDecoratorBuilder {
        return config(DecoratorConfigBuilder()) {}
    }
}