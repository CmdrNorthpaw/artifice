package com.swordglowsblue.artifice.api.builder.data.worldgen.gen

import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder
import com.swordglowsblue.artifice.api.builder.data.worldgen.UniformIntDistributionBuilder
import net.minecraft.util.Identifier
import java.util.function.Function

sealed class FoliagePlacerBuilder<out F: FoliagePlacerBuilder<F>>(
    type: Identifier
) : TypedJsonBuilder<JsonObject>(JsonObject(), { j: JsonObject -> j }) {
    protected abstract val me: F

    init {
        this.root.addProperty("type", type.toString())
    }

    fun radius(radius: Int): F {
        this.root.addProperty("radius", radius)
        return me
    }

    fun radius(processor: UniformIntDistributionBuilder.() -> Unit): F {
        with("radius", { JsonObject() }) { jsonObject: JsonObject ->
            UniformIntDistributionBuilder().apply(processor).buildTo(jsonObject)
        }
        return me
    }

    fun offset(offset: Int): F {
        this.root.addProperty("offset", offset)
        return me
    }

    fun offset(processor: UniformIntDistributionBuilder.() -> Unit): F {
        with("offset", { JsonObject() }) { jsonObject: JsonObject ->
            UniformIntDistributionBuilder().apply(processor).buildTo(jsonObject)
        }
        return me
    }

    sealed class BlobFoliagePlacerBuilder<out F: BlobFoliagePlacerBuilder<F>>(
        type: Identifier = Identifier("blob_foliage_placer")
    ) : FoliagePlacerBuilder<F>(type) {
        fun height(height: Int): F {
            require(height <= 16) { "Height can't be higher than 16! Found $height" }
            require(height >= 0) { "Height can't be smaller than 0! Found $height" }
            this.root.addProperty("height", height)
            return me 
        }
        
    }

    class SpruceFoliagePlacerBuilder : FoliagePlacerBuilder<SpruceFoliagePlacerBuilder>(
        Identifier("spruce_foliage_placer")
    ) {
        override val me: SpruceFoliagePlacerBuilder
            get() = this
        
        fun trunkHeight(trunkHeight: Int): SpruceFoliagePlacerBuilder {
            this.root.addProperty("trunk_height", trunkHeight)
            return this
        }

        fun trunkHeight(processor: UniformIntDistributionBuilder.() -> Unit): SpruceFoliagePlacerBuilder {
            with("trunk_height", { JsonObject() }) { jsonObject: JsonObject ->
                UniformIntDistributionBuilder().apply(processor).buildTo(jsonObject)
            }
            return this
        }
    }

    class PineFoliagePlacerBuilder : FoliagePlacerBuilder<PineFoliagePlacerBuilder>(Identifier("pine_foliage_placer")) {
        override val me: PineFoliagePlacerBuilder
            get() = this
        
        fun trunkHeight(offset: Int): PineFoliagePlacerBuilder {
            this.root.addProperty("trunk_height", offset)
            return this
        }

        fun trunkHeight(processor: UniformIntDistributionBuilder.() -> Unit): PineFoliagePlacerBuilder {
            with("trunk_height", { JsonObject() }) { jsonObject: JsonObject ->
                UniformIntDistributionBuilder().apply(processor).buildTo(jsonObject)
            }
            return this
        }
    }

    class AcaciaFoliagePlacerBuilder : FoliagePlacerBuilder<AcaciaFoliagePlacerBuilder>(Identifier("acacia_foliage_placer")) {
        override val me: AcaciaFoliagePlacerBuilder
            get() = this
    }

    class BushFoliagePlacerBuilder : BlobFoliagePlacerBuilder<BushFoliagePlacerBuilder>(Identifier("bush_foliage_placer")) {
        override val me: BushFoliagePlacerBuilder
            get() = this
    }

    class FancyFoliagePlacerBuilder : BlobFoliagePlacerBuilder<FancyFoliagePlacerBuilder>(
        Identifier("fancy_foliage_placer")
    ) {
        override val me: FancyFoliagePlacerBuilder
            get() = this
    }

    class JungleFoliagePlacerBuilder : FoliagePlacerBuilder<JungleFoliagePlacerBuilder>(Identifier("jungle_foliage_placer")) {
        override val me: JungleFoliagePlacerBuilder
            get() = this
        
        fun height(height: Int): JungleFoliagePlacerBuilder {
            require(height <= 16) { "Height can't be higher than 16! Found $height" }
            require(height >= 0) { "Height can't be smaller than 0! Found $height" }
            this.root.addProperty("height", height)
            return this
        }
    }

    class MegaPineFoliagePlacerBuilder : FoliagePlacerBuilder<MegaPineFoliagePlacerBuilder>(
        Identifier("mega_pine_foliage_placer")
    ) {
        override val me: MegaPineFoliagePlacerBuilder
            get() = this
        
        fun crownHeight(crownHeight: Int): MegaPineFoliagePlacerBuilder {
            this.root.addProperty("crown_height", crownHeight)
            return this
        }

        fun crownHeight(processor: UniformIntDistributionBuilder.() -> Unit): MegaPineFoliagePlacerBuilder {
            with("crown_height", { JsonObject() }) { jsonObject: JsonObject ->
                UniformIntDistributionBuilder().apply(processor).buildTo(jsonObject)
            }
            return this
        }
    }

    class DarkOakFoliagePlacerBuilder : FoliagePlacerBuilder<DarkOakFoliagePlacerBuilder>(
        Identifier("dark_oak_foliage_placer")
    ) {
        override val me: DarkOakFoliagePlacerBuilder
            get() = this
    }
}