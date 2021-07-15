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

sealed class TrunkPlacerBuilder<out B: TrunkPlacerBuilder<B>>(
    type: Identifier
) : TypedJsonBuilder<JsonObject?>(JsonObject(), Function { j: JsonObject? -> j }) {
    protected abstract val me: B

    init {
        root.addProperty("type", type.toString())
    }
    
    fun type(type: String?): B {
        this.root.addProperty("type", type)
        return me
    }

    fun baseHeight(base_height: Int): B {
        require(base_height <= 32) { "base_height can't be higher than 32! Found $base_height" }
        require(base_height >= 0) { "base_height can't be smaller than 0! Found $base_height" }
        this.root.addProperty("base_height", base_height)
        return me
    }

    fun heightRandA(height_rand_a: Int): B {
        require(height_rand_a <= 24) { "height_rand_a can't be higher than 24! Found $height_rand_a" }
        require(height_rand_a >= 0) { "height_rand_a can't be smaller than 0! Found $height_rand_a" }
        this.root.addProperty("height_rand_a", height_rand_a)
        return me
    }

    fun heightRandB(height_rand_b: Int): B {
        require(height_rand_b <= 24) { "height_rand_b can't be higher than 24! Found $height_rand_b" }
        require(height_rand_b >= 0) { "height_rand_b can't be smaller than 0! Found $height_rand_b" }
        this.root.addProperty("height_rand_b", height_rand_b)
        return me
    }

    class StraightTrunkPlacerBuilder : TrunkPlacerBuilder<StraightTrunkPlacerBuilder>(
        Identifier("straight_trunk_placer")
    ) {
        override val me: StraightTrunkPlacerBuilder
            get() = this
    }

    class ForkingTrunkPlacerBuilder : TrunkPlacerBuilder<ForkingTrunkPlacerBuilder>(
        Identifier("forking_trunk_placer")
    ) {
        override val me: ForkingTrunkPlacerBuilder
            get() = this
    }

    sealed class GiantTrunkPlacerBuilder<out G: GiantTrunkPlacerBuilder<G>>(
        type: Identifier = Identifier("giant_trunk_placer")
    ) : TrunkPlacerBuilder<G>(type)

    class MegaJungleTrunkPlacerBuilder : GiantTrunkPlacerBuilder<MegaJungleTrunkPlacerBuilder>(
        Identifier("mega_jungle_trunk_placer")
    ) {
        override val me: MegaJungleTrunkPlacerBuilder
            get() = this
    }

    class DarkOakTrunkPlacerBuilder : TrunkPlacerBuilder<DarkOakTrunkPlacerBuilder>(Identifier("dark_oak_trunk_placer")) {
        override val me: DarkOakTrunkPlacerBuilder
            get() = this
    }

    class FancyTrunkPlacerBuilder : TrunkPlacerBuilder<FancyTrunkPlacerBuilder>(Identifier("fancy_trunk_placer")) {
        override val me: FancyTrunkPlacerBuilder
            get() = this
    }
}