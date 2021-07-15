package com.swordglowsblue.artifice.api.builder.data.worldgen.configured

import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder
import com.swordglowsblue.artifice.api.builder.data.StateDataBuilder
import com.swordglowsblue.artifice.api.resource.JsonResource
import java.util.function.Function

class ConfiguredSurfaceBuilder : TypedJsonBuilder<JsonResource<JsonObject?>?>(
    JsonObject(),
    Function<JsonObject?, JsonResource<JsonObject?>?> { root: JsonObject? -> JsonResource(root) }) {
    /**
     * Set a block state.
     * @param id
     * @param blockStateBuilderProcessor
     * @return
     */
    private fun setBlockState(
        id: String,
        blockStateBuilderProcessor: StateDataBuilder.() -> Unit
    ): ConfiguredSurfaceBuilder {
        with(
            this.root.getAsJsonObject("config"),
            id,
            { JsonObject() }) { jsonObject: JsonObject? ->
            StateDataBuilder().apply(blockStateBuilderProcessor).buildTo(jsonObject)
        }
        return this
    }

    fun topMaterial(blockStateBuilderProcessor: StateDataBuilder.() -> Unit): ConfiguredSurfaceBuilder {
        return setBlockState("top_material", blockStateBuilderProcessor)
    }

    fun underMaterial(blockStateBuilderProcessor: StateDataBuilder.() -> Unit): ConfiguredSurfaceBuilder {
        return setBlockState("under_material", blockStateBuilderProcessor)
    }

    fun underwaterMaterial(blockStateBuilderProcessor: StateDataBuilder.() -> Unit): ConfiguredSurfaceBuilder {
        return setBlockState("underwater_material", blockStateBuilderProcessor)
    }

    fun surfaceBuilderID(id: String?): ConfiguredSurfaceBuilder {
        this.root.addProperty("type", id)
        return this
    }

    init {
        this.root.add("config", JsonObject())
    }
}