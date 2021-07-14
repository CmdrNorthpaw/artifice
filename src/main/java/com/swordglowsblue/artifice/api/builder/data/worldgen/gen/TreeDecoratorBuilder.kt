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
import net.minecraft.util.Identifier
import java.util.function.Function

sealed class TreeDecoratorBuilder(
    type: Identifier
) : TypedJsonBuilder<JsonObject?>(JsonObject(), Function { j: JsonObject? -> j }) {
    init {
        root.addProperty("type", type.toString())
    }

    class TrunkVineTreeDecoratorBuilder : TreeDecoratorBuilder(Identifier("trunk_vine"))

    class LeaveVineTreeDecoratorBuilder : TreeDecoratorBuilder(Identifier("leave_vine"))

    class CocoaTreeDecoratorBuilder : TreeDecoratorBuilder(Identifier("cocoa")) {
        fun probability(probability: Float): CocoaTreeDecoratorBuilder {
            require(probability <= 1.0f) { "probability can't be higher than 1.0F! Found $probability" }
            require(probability >= 0.0f) { "probability can't be smaller than 0.0F! Found $probability" }
            this.root.addProperty("probability", probability)
            return this
        }
    }

    class BeeHiveTreeDecoratorBuilder : TreeDecoratorBuilder(Identifier("beehive")) {
        fun probability(probability: Float): BeeHiveTreeDecoratorBuilder {
            require(probability <= 1.0f) { "probability can't be higher than 1.0F! Found $probability" }
            require(probability >= 0.0f) { "probability can't be smaller than 0.0F! Found $probability" }
            this.root.addProperty("probability", probability)
            return this
        }
    }

    class AlterGroundTreeDecoratorBuilder : TreeDecoratorBuilder(Identifier("alter_ground")) {
        fun <P : BlockStateProviderBuilder> provider(
            instance: P,
            processor: P.() -> Unit
        ): AlterGroundTreeDecoratorBuilder {
            with("provider", { JsonObject() }) { jsonObject: JsonObject? ->
                instance.apply(processor).buildTo(jsonObject)
            }
            return this
        }
    }
}