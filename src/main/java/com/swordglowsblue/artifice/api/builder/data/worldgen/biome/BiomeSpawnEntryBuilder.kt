package com.swordglowsblue.artifice.api.builder.data.worldgen.biome

import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder
import com.swordglowsblue.artifice.api.resource.JsonResource
import com.google.gson.JsonObject
import net.minecraft.world.biome.Biome.Precipitation
import net.minecraft.world.biome.Biome
import com.swordglowsblue.artifice.api.builder.data.worldgen.biome.BiomeEffectsBuilder
import com.swordglowsblue.artifice.api.builder.data.worldgen.biome.BiomeBuilder.SpawnDensityBuilder
import net.minecraft.entity.SpawnGroup
import com.swordglowsblue.artifice.api.builder.data.worldgen.biome.BiomeSpawnEntryBuilder
import net.minecraft.world.gen.GenerationStep
import com.google.gson.JsonArray
import com.swordglowsblue.artifice.api.builder.data.worldgen.biome.BiomeEffectsBuilder.BiomeMoodSoundBuilder
import com.swordglowsblue.artifice.api.builder.data.worldgen.biome.BiomeEffectsBuilder.BiomeAdditionsSoundBuilder
import com.swordglowsblue.artifice.api.builder.data.worldgen.biome.BiomeEffectsBuilder.BiomeMusicSoundBuilder
import com.swordglowsblue.artifice.api.builder.data.worldgen.biome.BiomeEffectsBuilder.BiomeParticleConfigBuilder
import java.util.function.Function

class BiomeSpawnEntryBuilder : TypedJsonBuilder<JsonObject?>(JsonObject(), Function { j: JsonObject? -> j }) {
    fun entityID(entityID: String?): BiomeSpawnEntryBuilder {
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