package com.swordglowsblue.artifice.api.builder.data.worldgen

import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder
import java.util.function.Function

class UniformIntDistributionBuilder : TypedJsonBuilder<JsonObject>(JsonObject(), { j: JsonObject -> j }) {
    fun base(base: Int): UniformIntDistributionBuilder {
        this.root.addProperty("base", base)
        return this
    }

    fun spread(spread: Int): UniformIntDistributionBuilder {
        this.root.addProperty("spread", spread)
        return this
    }
}