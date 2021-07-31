package com.swordglowsblue.artifice.api.builder.data.worldgen

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder
import com.swordglowsblue.artifice.api.builder.data.StateDataBuilder
import com.swordglowsblue.artifice.api.util.Builder
import com.swordglowsblue.artifice.api.util.Processor
import net.minecraft.util.Identifier
import java.util.function.Function

sealed class BlockStateProviderBuilder(
    type: Identifier
) : TypedJsonBuilder<JsonObject>(JsonObject(), { j: JsonObject -> j }) {

    init {
       root.addProperty("type", type.toString())
    }

    class SimpleBlockStateProviderBuilder
        : BlockStateProviderBuilder(Identifier("minecraft", "simple_state_provider")) {
        fun state(processor: Builder<StateDataBuilder>): SimpleBlockStateProviderBuilder {
            with("state", { JsonObject() }) { jsonObject: JsonObject ->
                StateDataBuilder().apply(processor).buildTo(jsonObject)
            }
            return this
        }
    }

    class WeightedBlockStateProviderBuilder
        : BlockStateProviderBuilder(Identifier("minecraft", "weighted_state_provider")) {
        fun addEntry(weight: Int, processor: Builder<StateDataBuilder>): WeightedBlockStateProviderBuilder {
            val jsonObject = JsonObject()
            jsonObject.addProperty("weight", weight)
            jsonObject.add("data", StateDataBuilder().apply(processor).buildTo(JsonObject()))
            this.root.getAsJsonArray("entries").add(jsonObject)
            return this
        }

        init {
            this.root.add("entries", JsonArray())
        }
    }

    class PlainFlowerBlockStateProviderBuilder
        : BlockStateProviderBuilder(Identifier("minecraft", "plain_flower_provider"))

    class ForestFlowerBlockStateProviderBuilder
        : BlockStateProviderBuilder(Identifier("minecraft", "forest_flower_provider"))

    class PillarBlockStateProviderBuilder
        : BlockStateProviderBuilder(Identifier("minecraft", "pillar_state_provider")) {
        fun state(processor: Builder<StateDataBuilder>): PillarBlockStateProviderBuilder {
            with("state", { JsonObject() }) { jsonObject: JsonObject ->
                StateDataBuilder().apply(processor).buildTo(jsonObject)
            }
            return this
        }
    }
}