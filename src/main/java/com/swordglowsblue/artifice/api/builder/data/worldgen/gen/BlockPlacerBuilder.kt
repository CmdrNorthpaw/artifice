package com.swordglowsblue.artifice.api.builder.data.worldgen.gen

import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder
import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.data.worldgen.gen.BlockPlacerBuilder
import com.swordglowsblue.artifice.api.builder.data.worldgen.gen.BlockPlacerBuilder.ColumnPlacerBuilder
import com.swordglowsblue.artifice.api.builder.data.worldgen.gen.FeatureSizeBuilder
import java.lang.IllegalArgumentException
import com.swordglowsblue.artifice.api.builder.data.worldgen.gen.FeatureSizeBuilder.TwoLayersFeatureSizeBuilder
import com.swordglowsblue.artifice.api.builder.data.worldgen.gen.FeatureSizeBuilder.ThreeLayersFeatureSizeBuilder
import com.swordglowsblue.artifice.api.builder.data.worldgen.gen.TrunkPlacerBuilder
import com.swordglowsblue.artifice.api.builder.data.worldgen.gen.TrunkPlacerBuilder.GiantTrunkPlacerBuilder
import com.swordglowsblue.artifice.api.builder.data.worldgen.gen.FoliagePlacerBuilder
import com.swordglowsblue.artifice.api.builder.data.worldgen.UniformIntDistributionBuilder
import com.swordglowsblue.artifice.api.builder.data.worldgen.gen.FoliagePlacerBuilder.BlobFoliagePlacerBuilder
import com.swordglowsblue.artifice.api.builder.data.worldgen.gen.FoliagePlacerBuilder.SpruceFoliagePlacerBuilder
import com.swordglowsblue.artifice.api.builder.data.worldgen.gen.FoliagePlacerBuilder.PineFoliagePlacerBuilder
import com.swordglowsblue.artifice.api.builder.data.worldgen.gen.FoliagePlacerBuilder.JungleFoliagePlacerBuilder
import com.swordglowsblue.artifice.api.builder.data.worldgen.gen.FoliagePlacerBuilder.MegaPineFoliagePlacerBuilder
import com.swordglowsblue.artifice.api.builder.data.worldgen.gen.TreeDecoratorBuilder
import com.swordglowsblue.artifice.api.builder.data.worldgen.gen.TreeDecoratorBuilder.CocoaTreeDecoratorBuilder
import com.swordglowsblue.artifice.api.builder.data.worldgen.gen.TreeDecoratorBuilder.BeeHiveTreeDecoratorBuilder
import com.swordglowsblue.artifice.api.builder.data.worldgen.BlockStateProviderBuilder
import com.swordglowsblue.artifice.api.builder.data.worldgen.gen.TreeDecoratorBuilder.AlterGroundTreeDecoratorBuilder
import sun.tools.jstat.Identifier
import java.util.function.Function

sealed class BlockPlacerBuilder(
    type: Identifier
) : TypedJsonBuilder<JsonObject?>(JsonObject(), Function { j: JsonObject? -> j }) {

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