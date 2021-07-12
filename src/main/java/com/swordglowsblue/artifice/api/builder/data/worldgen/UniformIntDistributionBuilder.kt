package com.swordglowsblue.artifice.api.builder.data.worldgen

import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder
import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.data.worldgen.UniformIntDistributionBuilder
import java.util.function.Function

class UniformIntDistributionBuilder : TypedJsonBuilder<JsonObject?>(JsonObject(), Function { j: JsonObject? -> j }) {
    fun base(base: Int): UniformIntDistributionBuilder {
        this.root.addProperty("base", base)
        return this
    }

    fun spread(spread: Int): UniformIntDistributionBuilder {
        this.root.addProperty("spread", spread)
        return this
    }
}