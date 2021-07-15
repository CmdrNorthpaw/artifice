package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.decorator.config

import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.data.worldgen.UniformIntDistributionBuilder
import com.swordglowsblue.artifice.api.util.Processor

class CountConfigBuilder : DecoratorConfigBuilder() {
    fun count(count: Int): CountConfigBuilder {
        this.root.addProperty("count", count)
        return this
    }

    fun count(processor: Processor<UniformIntDistributionBuilder>): CountConfigBuilder {
        with("count", { JsonObject() }) { jsonObject: JsonObject? ->
            processor.process(UniformIntDistributionBuilder()).buildTo(jsonObject)
        }
        return this
    }
}