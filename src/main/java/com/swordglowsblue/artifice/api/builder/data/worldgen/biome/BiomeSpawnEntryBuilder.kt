package com.swordglowsblue.artifice.api.builder.data.worldgen.biome

import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder
import java.util.function.Function

class BiomeSpawnEntryBuilder : TypedJsonBuilder<JsonObject>(JsonObject(), { j: JsonObject -> j }) {
    fun entityID(entityID: String): BiomeSpawnEntryBuilder {
        this.root.addProperty("type", entityID)
        return this
    }

    fun weight(weight: Int): BiomeSpawnEntryBuilder {
        this.root.addProperty("weight", weight)
        return this
    }

    fun minCount(minCount: Int): BiomeSpawnEntryBuilder {
        this.root.addProperty("minCount", minCount)
        return this
    }

    fun maxCount(maxCount: Int): BiomeSpawnEntryBuilder {
        this.root.addProperty("maxCount", maxCount)
        return this
    }
}