package com.swordglowsblue.artifice.api.builder.data.worldgen.biome

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder
import com.swordglowsblue.artifice.api.resource.JsonResource
import com.swordglowsblue.artifice.api.util.IdUtils.asId
import net.minecraft.entity.SpawnGroup
import net.minecraft.util.Identifier
import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.Biome.Precipitation
import net.minecraft.world.gen.GenerationStep
import java.util.function.Function

class BiomeBuilder : TypedJsonBuilder<JsonResource<JsonObject>>(
    JsonObject(), { root: JsonObject -> JsonResource(root) }
) {
    fun depth(depth: Float) = apply { this.depth = depth }

    var depth: Float?
    get() = root["depth"]?.asFloat
    set(value) = root.addProperty("depth", value)

    fun scale(scale: Float) = apply { this.scale = scale }

    var scale: Float?
    get() = root["scale"]?.asFloat
    set(value) = root.addProperty("scale", value)

    fun temperature(temperature: Float) = apply { this.temperature = temperature }

    var temperature: Float?
    get() = root["temperature"]?.asFloat
    set(value) = root.addProperty("temperature", value)

    fun downfall(downfall: Float) = apply { this.downfall = downfall }

    var downfall: Float?
    get() = root["downfall"]?.asFloat
    set(value) = root.addProperty("downfall", value)

    fun parent(parent: Identifier) = apply { this.parent = parent }

    var parent: Identifier?
    get() = root["parent"].asId
    set(value) = root.addProperty("parent", value?.toString())

    fun surfaceBuilder(builder: Identifier) = apply { this.surfaceBuilder = builder }

    var surfaceBuilder: Identifier?
    get() = root["surface_builder"].asId
    set(value) = root.addProperty("surface_builder", value?.toString())

    fun precipitation(precipitation: Precipitation) = apply { this.precipitation = precipitation }

    var precipitation: Precipitation?
    get() = Precipitation.byName(root["precipitation"].asString)
    set(value) = root.addProperty("precipitation", value?.name)

    fun category(category: Biome.Category) = apply { this.category = category }

    var category: Biome.Category?
    get() = Biome.Category.byName(root["category"]?.asString)
    set(value) = root.addProperty("category", value?.getName())

    fun effects(biomeEffectsBuilder: BiomeEffectsBuilder.() -> Unit) = apply {
        with("effects", { JsonObject() }) { biomeEffects: JsonObject ->
            BiomeEffectsBuilder().apply(biomeEffectsBuilder).buildTo(biomeEffects)
        }
    }

    fun addSpawnCosts(entityID: String, spawnDensityBuilderPtrocessor: SpawnDensityBuilder.() -> Unit) = apply {
        with(entityID, { JsonObject() }) { spawnDensityBuilder: JsonObject ->
            SpawnDensityBuilder().apply(spawnDensityBuilderPtrocessor).buildTo(spawnDensityBuilder)
        }
    }

    fun addSpawnEntry(
        spawnGroup: SpawnGroup, biomeSpawnEntryBuilder: BiomeSpawnEntryBuilder.() -> Unit) = apply {
        this.root.getAsJsonObject("spawners")[spawnGroup.getName()].asJsonArray
            .add(BiomeSpawnEntryBuilder().apply(biomeSpawnEntryBuilder).buildTo(JsonObject()))
    }

    private fun addCarver(carver: GenerationStep.Carver, configuredCaverIDs: Array<out Identifier>) {
        configuredCaverIDs.forEach {
            this.root.getAsJsonObject("carvers").getAsJsonArray(carver.getName()).add(it.toString())
        }
    }

    fun addAirCarvers(vararg configuredCarverIds: Identifier) = apply {
        this.root.getAsJsonObject("carvers").add(GenerationStep.Carver.AIR.getName(), JsonArray())
        addCarver(GenerationStep.Carver.AIR, configuredCarverIds)
    }

    fun addLiquidCarvers(vararg configuredCarverIds: Identifier): BiomeBuilder {
        this.root.getAsJsonObject("carvers").add(GenerationStep.Carver.LIQUID.getName(), JsonArray())
        addCarver(GenerationStep.Carver.LIQUID, configuredCarverIds)
        return this
    }

    fun addFeaturesbyStep(step: GenerationStep.Feature, vararg featureIDs: Identifier) = apply {
        featureIDs.forEach {
            this.root.getAsJsonArray("features")[step.ordinal].asJsonArray.add(it.toString())
        }
    }

    fun addStructure(structureID: String) = apply {
        this.root.getAsJsonArray("starts").add(structureID)
    }

    class SpawnDensityBuilder : TypedJsonBuilder<JsonObject>(JsonObject(), { j: JsonObject -> j }) {
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