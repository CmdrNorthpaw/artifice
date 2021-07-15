package com.swordglowsblue.artifice.api.builder.data.worldgen.biome

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder
import com.swordglowsblue.artifice.api.resource.JsonResource
import net.minecraft.entity.SpawnGroup
import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.Biome.Precipitation
import net.minecraft.world.gen.GenerationStep
import java.util.function.Function

class BiomeBuilder : TypedJsonBuilder<JsonResource<JsonObject?>?>(
    JsonObject(),
    Function<JsonObject?, JsonResource<JsonObject?>?> { root: JsonObject? -> JsonResource(root) }) {
    fun depth(depth: Float): BiomeBuilder {
        this.root.addProperty("depth", depth)
        return this
    }

    fun scale(scale: Float): BiomeBuilder {
        this.root.addProperty("scale", scale)
        return this
    }

    fun temperature(temperature: Float): BiomeBuilder {
        this.root.addProperty("temperature", temperature)
        return this
    }

    fun downfall(downfall: Float): BiomeBuilder {
        this.root.addProperty("downfall", downfall)
        return this
    }

    fun parent(parent: String?): BiomeBuilder {
        this.root.addProperty("parent", parent)
        return this
    }

    fun surfaceBuilder(surface_builder: String?): BiomeBuilder {
        this.root.addProperty("surface_builder", surface_builder)
        return this
    }

    fun precipitation(precipitation: Precipitation): BiomeBuilder {
        this.root.addProperty("precipitation", precipitation.getName())
        return this
    }

    fun category(category: Biome.Category): BiomeBuilder {
        this.root.addProperty("category", category.getName())
        return this
    }

    fun effects(biomeEffectsBuilder: BiomeEffectsBuilder.() -> Unit): BiomeBuilder {
        with(
            "effects",
            { JsonObject() }) { biomeEffects: JsonObject? ->
            BiomeEffectsBuilder().also(biomeEffectsBuilder).buildTo(biomeEffects)
        }
        return this
    }

    fun addSpawnCosts(entityID: String?, spawnDensityBuilderProcessor: SpawnDensityBuilder.() -> Unit): BiomeBuilder {
        with(entityID, { JsonObject() }) { spawnDensityBuilder: JsonObject? ->
            SpawnDensityBuilder().also(spawnDensityBuilderProcessor).buildTo(spawnDensityBuilder)
        }
        return this
    }

    fun addSpawnEntry(
        spawnGroup: SpawnGroup,
        biomeSpawnEntryBuilderProcessor: BiomeSpawnEntryBuilder.() -> Unit
    ): BiomeBuilder {
        this.root.getAsJsonObject("spawners")[spawnGroup.getName()].asJsonArray
            .add(BiomeSpawnEntryBuilder().also(biomeSpawnEntryBuilderProcessor).buildTo(JsonObject()))
        return this
    }

    private fun addCarver(carver: GenerationStep.Carver, configuredCaverIDs: Array<out String>): BiomeBuilder {
        for (configuredCaverID in configuredCaverIDs) this.root.getAsJsonObject("carvers")
            .getAsJsonArray(carver.getName()).add(configuredCaverID)
        return this
    }

    fun addAirCarvers(vararg configuredCarverIds: String): BiomeBuilder {
        this.root.getAsJsonObject("carvers").add(GenerationStep.Carver.AIR.getName(), JsonArray())
        addCarver(GenerationStep.Carver.AIR, configuredCarverIds)
        return this
    }

    fun addLiquidCarvers(vararg configuredCarverIds: String): BiomeBuilder {
        this.root.getAsJsonObject("carvers").add(GenerationStep.Carver.LIQUID.getName(), JsonArray())
        addCarver(GenerationStep.Carver.LIQUID, configuredCarverIds)
        return this
    }

    fun addFeaturesbyStep(step: GenerationStep.Feature, vararg featureIDs: String?): BiomeBuilder {
        for (featureID in featureIDs) this.root.getAsJsonArray("features")[step.ordinal].asJsonArray.add(featureID)
        return this
    }

    fun addStructure(structureID: String?): BiomeBuilder {
        this.root.getAsJsonArray("starts").add(structureID)
        return this
    }

    class SpawnDensityBuilder : TypedJsonBuilder<JsonObject?>(JsonObject(), Function { j: JsonObject? -> j }) {
        fun energyBudget(energy_budget: Double): SpawnDensityBuilder {
            this.root.addProperty("energy_budget", energy_budget)
            return this
        }

        fun charge(charge: Double): SpawnDensityBuilder {
            this.root.addProperty("charge", charge)
            return this
        }
    }

    init {
        this.root.add("carvers", JsonObject())
        this.root.add("starts", JsonArray())
        this.root.add("features", JsonArray())
        for (step in GenerationStep.Feature.values()) {
            this.root.getAsJsonArray("features").add(JsonArray())
        }
        this.root.add("spawn_costs", JsonObject())
        this.root.add("spawners", JsonObject())
        for (spawnGroup in SpawnGroup.values()) {
            this.root.getAsJsonObject("spawners").add(spawnGroup.getName(), JsonArray())
        }
    }
}