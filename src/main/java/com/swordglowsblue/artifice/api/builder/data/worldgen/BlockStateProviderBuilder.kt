package com.swordglowsblue.artifice.api.builder.data.worldgen

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder
import com.swordglowsblue.artifice.api.builder.data.StateDataBuilder
import com.swordglowsblue.artifice.api.util.Processor
import java.util.function.Function

open class BlockStateProviderBuilder : TypedJsonBuilder<JsonObject>(JsonObject(), { j: JsonObject -> j }) {
    fun <P : BlockStateProviderBuilder> type(type: String): P {
        this.root.addProperty("type", type)
        return this as P
    }

    class SimpleBlockStateProviderBuilder : BlockStateProviderBuilder() {
        fun state(processor: Processor<StateDataBuilder>): SimpleBlockStateProviderBuilder {
            with("state", { JsonObject() }) { jsonObject: JsonObject ->
                processor.process(StateDataBuilder()).buildTo(jsonObject)
            }
            return this
        }

        init {
            type<BlockStateProviderBuilder>("minecraft:simple_state_provider")
        }
    }

    class WeightedBlockStateProviderBuilder : BlockStateProviderBuilder() {
        fun addEntry(weight: Int, processor: Processor<StateDataBuilder>): WeightedBlockStateProviderBuilder {
            val jsonObject = JsonObject()
            jsonObject.addProperty("weight", weight)
            jsonObject.add("data", processor.process(StateDataBuilder()).buildTo(JsonObject()))
            this.root.getAsJsonArray("entries").add(jsonObject)
            return this
        }

        init {
            type<BlockStateProviderBuilder>("minecraft:weighted_state_provider")
            this.root.add("entries", JsonArray())
        }
    }

    class PlainFlowerBlockStateProviderBuilder : BlockStateProviderBuilder() {
        init {
            type<BlockStateProviderBuilder>("minecraft:plain_flower_provider")
        }
    }

    class ForestFlowerBlockStateProviderBuilder : BlockStateProviderBuilder() {
        init {
            type<BlockStateProviderBuilder>("minecraft:forest_flower_provider")
        }
    }

    class PillarBlockStateProviderBuilder : BlockStateProviderBuilder() {
        fun state(processor: Processor<StateDataBuilder>): PillarBlockStateProviderBuilder {
            with("state", { JsonObject() }) { jsonObject: JsonObject ->
                processor.process(StateDataBuilder()).buildTo(jsonObject)
            }
            return this
        }

        init {
            type<BlockStateProviderBuilder>("minecraft:pillar_state_provider")
        }
    }
}