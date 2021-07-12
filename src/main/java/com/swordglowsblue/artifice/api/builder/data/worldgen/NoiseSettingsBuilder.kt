package com.swordglowsblue.artifice.api.builder.data.worldgen

import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder
import com.swordglowsblue.artifice.api.resource.JsonResource
import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.data.worldgen.NoiseSettingsBuilder
import com.swordglowsblue.artifice.api.builder.data.dimension.NoiseConfigBuilder
import com.swordglowsblue.artifice.api.builder.data.StateDataBuilder
import com.swordglowsblue.artifice.api.builder.data.dimension.StructureManagerBuilder
import com.swordglowsblue.artifice.api.util.Processor
import java.util.function.Function

class NoiseSettingsBuilder : TypedJsonBuilder<JsonResource<JsonObject?>?>(
    JsonObject(),
    Function<JsonObject?, JsonResource<JsonObject?>?> { root: JsonObject? -> JsonResource(root) }) {
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
        with("noise", { JsonObject() }) { jsonObject: JsonObject? ->
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
    fun setBlockState(id: String?, blockStateBuilderProcessor: Processor<StateDataBuilder>): NoiseSettingsBuilder {
        with(id, { JsonObject() }) { jsonObject: JsonObject? ->
            blockStateBuilderProcessor.process(StateDataBuilder()).buildTo(jsonObject)
        }
        return this
    }

    /**
     * Build default block.
     * @param blockStateBuilderProcessor
     * @return
     */
    fun defaultBlock(blockStateBuilderProcessor: Processor<StateDataBuilder>): NoiseSettingsBuilder {
        return setBlockState("default_block", blockStateBuilderProcessor)
    }

    /**
     * Build default fluid.
     * @param blockStateBuilderProcessor
     * @return
     */
    fun defaultFluid(blockStateBuilderProcessor: Processor<StateDataBuilder>): NoiseSettingsBuilder {
        return setBlockState("default_fluid", blockStateBuilderProcessor)
    }

    /**
     * Build structure manager.
     * @param structureManagerBuilder
     * @return
     */
    fun structureManager(structureManagerBuilder: Processor<StructureManagerBuilder>): NoiseSettingsBuilder {
        with("structures", { JsonObject() }) { jsonObject: JsonObject? ->
            structureManagerBuilder.process(
                StructureManagerBuilder()
            ).buildTo(jsonObject)
        }
        return this
    }
}