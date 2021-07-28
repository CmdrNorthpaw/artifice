package com.swordglowsblue.artifice.api.builder.data.worldgen.gen

import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder
import net.minecraft.util.Identifier
import java.util.function.Function

sealed class TrunkPlacerBuilder<out B: TrunkPlacerBuilder<B>>(
    type: Identifier
) : TypedJsonBuilder<JsonObject>(JsonObject(), { j: JsonObject -> j }) {
    protected abstract val me: B

    init {
        root.addProperty("type", type.toString())
    }
    
    fun type(type: String): B {
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