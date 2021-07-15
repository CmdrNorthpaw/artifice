package com.swordglowsblue.artifice.api.builder.data.worldgen.gen

import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder
import net.minecraft.util.Identifier
import java.util.function.Function

sealed class BlockPlacerBuilder(
    type: Identifier
) : TypedJsonBuilder<JsonObject>(JsonObject(), Function { j: JsonObject -> j }) {

    init {
        root.addProperty("type", type.toString())
    }

    class SimpleBlockPlacerBuilder : BlockPlacerBuilder(Identifier("simple_block_placer"))

    class DoublePlantPlacerBuilder : BlockPlacerBuilder(Identifier("double_plant_placer"))

    class ColumnPlacerBuilder : BlockPlacerBuilder(Identifier("column_placer")) {
        fun minSize(minSize: Int): ColumnPlacerBuilder {
            this.root.addProperty("min_size", minSize)
            return this
        }

        fun extraSize(extraSize: Int): ColumnPlacerBuilder {
            this.root.addProperty("extra_size", extraSize)
            return this
        }
    }
}