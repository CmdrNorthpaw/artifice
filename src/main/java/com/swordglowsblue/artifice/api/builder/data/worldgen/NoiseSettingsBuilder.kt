package com.swordglowsblue.artifice.api.builder.data.worldgen

import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder
import com.swordglowsblue.artifice.api.builder.data.StateDataBuilder
import com.swordglowsblue.artifice.api.builder.data.dimension.NoiseConfigBuilder
import com.swordglowsblue.artifice.api.builder.data.dimension.StructureManagerBuilder
import com.swordglowsblue.artifice.api.resource.JsonResource
import java.util.function.Function

class NoiseSettingsBuilder : TypedJsonBuilder<JsonResource<JsonObject>>(
    JsonObject(),
    { root: JsonObject -> JsonResource(root) }) {
    /**
     * Set the bedrock roof position.
     * @param bedrockRoofPosition
     * @return
     */
    fun bedrockRoofPosition(bedrockRoofPosition: Int): NoiseSettingsBuilder {
        this.root.addProperty("bedrock_roof_position", bedrockRoofPosition)
        return this
    }

    /**
     * Set the bedrock floor position.
     * @param bedrockFloorPosition
     * @return
     */
    fun bedrockFloorPosition(bedrockFloorPosition: Int): NoiseSettingsBuilder {
        this.root.addProperty("bedrock_floor_position", bedrockFloorPosition)
        return this
    }

    /**
     * Set the sea level.
     * @param seaLevel
     * @return
     */
    fun seaLevel(seaLevel: Int): NoiseSettingsBuilder {
        this.root.addProperty("sea_level", seaLevel)
        return this
    }

    /**
     * Build noise config.
     */
    fun noiseConfig(noiseConfigBuilder: NoiseConfigBuilder.() -> Unit): NoiseSettingsBuilder {
        with("noise", { JsonObject() }) { jsonObject: JsonObject ->
            NoiseConfigBuilder().also(noiseConfigBuilder).buildTo(jsonObject)
        }
        return this
    }

    fun disableMobGeneration(disableMobGeneration: Boolean): NoiseSettingsBuilder {
        this.root.addProperty("disable_mob_generation", disableMobGeneration)
        return this
    }

    fun aquifersEnabled(aquifersEnabled: Boolean): NoiseSettingsBuilder {
        this.root.addProperty("aquifers_enabled", aquifersEnabled)
        return this
    }

    fun noiseCavesEnabled(noiseCavesEnabled: Boolean): NoiseSettingsBuilder {
        this.root.addProperty("noise_caves_enabled", noiseCavesEnabled)
        return this
    }

    fun grimstoneEnabled(grimstoneEnabled: Boolean): NoiseSettingsBuilder {
        this.root.addProperty("grimstone_enabled", grimstoneEnabled)
        return this
    }

    /**
     * Set a block state.
     * @param id
     * @param blockStateBuilderProcessor
     * @return
     */
    fun setBlockState(id: String, blockStateBuilderProcessor: StateDataBuilder.() -> Unit): NoiseSettingsBuilder {
        with(id, { JsonObject() }) { jsonObject: JsonObject ->
            StateDataBuilder().also(blockStateBuilderProcessor).buildTo(jsonObject)
        }
        return this
    }

    /**
     * Build default block.
     * @param blockStateBuilderProcessor
     * @return
     */
    fun defaultBlock(blockStateBuilderProcessor: StateDataBuilder.() -> Unit): NoiseSettingsBuilder {
        return setBlockState("default_block", blockStateBuilderProcessor)
    }

    /**
     * Build default fluid.
     * @param blockStateBuilderProcessor
     * @return
     */
    fun defaultFluid(blockStateBuilderProcessor: StateDataBuilder.() -> Unit): NoiseSettingsBuilder {
        return setBlockState("default_fluid", blockStateBuilderProcessor)
    }

    /**
     * Build structure manager.
     * @param structureManagerBuilder
     * @return
     */
    fun structureManager(structureManagerBuilder: StructureManagerBuilder.() -> Unit): NoiseSettingsBuilder {
        with("structures", { JsonObject() }) { jsonObject: JsonObject ->
            StructureManagerBuilder().apply(structureManagerBuilder).buildTo(jsonObject)
        }
        return this
    }
}