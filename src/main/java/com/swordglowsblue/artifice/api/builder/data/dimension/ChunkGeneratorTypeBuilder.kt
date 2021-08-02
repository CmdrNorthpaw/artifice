package com.swordglowsblue.artifice.api.builder.data.dimension

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder
import com.swordglowsblue.artifice.api.builder.data.dimension.BiomeSourceBuilder.*
import com.swordglowsblue.artifice.api.dsl.ArtificeDsl
import com.swordglowsblue.artifice.api.util.IdUtils.addProperty
import com.swordglowsblue.artifice.api.util.IdUtils.asBlock
import com.swordglowsblue.artifice.api.util.IdUtils.asId
import com.swordglowsblue.artifice.api.util.IdUtils.id
import com.swordglowsblue.artifice.api.util.IdUtils.parseId
import net.minecraft.block.Block
import net.minecraft.util.Identifier
import java.util.function.Function

@ArtificeDsl
open class ChunkGeneratorTypeBuilder (type: Identifier) :
    TypedJsonBuilder<JsonObject>(JsonObject(), { j: JsonObject -> j })
{
    init {
        root.addProperty("type", type.toString())
    }

    /**
     * Set the biome Source.
     * @param instance
     * @param builder
     * @param <T>
     * @return
    </T> */
    fun <T : BiomeSourceBuilder<T>> biomeSource(instance: T, builder: T.() -> Unit) = apply {
        with("biome_source", { JsonObject() }) { biomeSource: JsonObject ->
            instance.apply(builder).buildTo(biomeSource)
        }
    }

    @ArtificeDsl
    class NoiseChunkGeneratorTypeBuilder : ChunkGeneratorTypeBuilder(Identifier("noise")) {
        /**
         * Set a seed specially for this dimension.
         * @param seed
         * @return
         */
        fun seed(seed: Int) = apply { this.seed = seed }

        var seed: Int?
        get() = root["seed"]?.asInt
        set(value) = root.addProperty("seed", value)

        @Deprecated("use noiseSettings instead.")
        fun presetSettings(presetId: String): NoiseChunkGeneratorTypeBuilder {
            noiseSettings(parseId(presetId)!!)
            return this
        }

        /**
         * Set Noise Settings to Id.
         * @param noiseSettingsID
         * @return
         */
        fun noiseSettings(noiseSettingsID: Identifier) = apply { noiseSettings = noiseSettingsID }

        var noiseSettings: Identifier?
        get() = root["settings"].asId
        set(value) = root.addProperty("settings", value.toString())

        /**
         * Build a vanilla layered biome source.
         * @param biomeSourceBuilder
         * @return
         */
        fun vanillaLayeredBiomeSource(biomeSourceBuilder: VanillaLayeredBiomeSourceBuilder.() -> Unit) = apply {
            biomeSource(VanillaLayeredBiomeSourceBuilder(), biomeSourceBuilder)
        }

        /**
         * Build a multi-noise biome source.
         * @param biomeSourceBuilder
         * @return
         */
        fun multiNoiseBiomeSource(biomeSourceBuilder: MultiNoiseBiomeSourceBuilder.() -> Unit) = apply {
            biomeSource(MultiNoiseBiomeSourceBuilder(), biomeSourceBuilder)
        }

        /**
         * Build a checkerboard biome source.
         * @param biomeSourceBuilder
         * @return
         */
        fun checkerboardBiomeSource(biomeSourceBuilder: CheckerboardBiomeSourceBuilder.() -> Unit) = apply {
            biomeSource(CheckerboardBiomeSourceBuilder(), biomeSourceBuilder)
        }

        /**
         * Build a fixed biome source.
         * @param biomeSourceBuilder
         * @return
         */
        fun fixedBiomeSource(biomeSourceBuilder: FixedBiomeSourceBuilder.() -> Unit) = apply {
            biomeSource(FixedBiomeSourceBuilder(), biomeSourceBuilder)
        }

        /**
         * Build a simple biome source.
         * @param id
         * @return
         */
        fun simpleBiomeSource(id: String) = apply {
            this.root.addProperty("biome_source", id)
        }
    }

    @ArtificeDsl
    class FlatChunkGeneratorTypeBuilder : ChunkGeneratorTypeBuilder(Identifier("flat")) {
        private val settings
        inline get() = root["settings"].asJsonObject

        /**
         * Build a structure manager.
         * @param structureManagerBuilder
         * @return
         */
        fun structureManager(structureManagerBuilder: StructureManagerBuilder.() -> Unit) = apply {
            with(settings, "structures", { JsonObject() }) { jsonObject: JsonObject ->
                StructureManagerBuilder().apply(structureManagerBuilder).buildTo(jsonObject)
            }
        }

        /**
         * Set the biome to biomeId.
         * @param biomeId
         * @return
         */
        fun biome(biomeId: Identifier) = apply { this.biomeId = biomeId }

        var biomeId: Identifier?
        get() = settings["biome"].asId
        set(value) = settings.addProperty("biome", value)

        fun generateLakes(lakes: Boolean) = apply { generateLakes = lakes }

        var generateLakes: Boolean?
        get() = settings["lakes"].asBoolean
        set(value) = settings.addProperty("lakes", value)

        fun generateFeatures(features: Boolean) = apply { generateFeatures = features }

        var generateFeatures: Boolean?
        get() = settings["features"].asBoolean
        set(value) = settings.addProperty("features", value)

        /**
         * Add a block layer.
         * @param layersBuilder
         * @return
         */
        fun addLayer(layersBuilder: LayersBuilder.() -> Unit) = apply {
            with(
                settings, "layers", { JsonArray() }) { jsonElements: JsonArray ->
                jsonElements.add(
                    LayersBuilder().apply(layersBuilder).buildTo(JsonObject())
                )
            }
        }

        class LayersBuilder : TypedJsonBuilder<JsonObject>(JsonObject(), { j: JsonObject -> j }) {
            /**
             * Set the height of the layer.
             * @param height
             * @return
             */
            fun height(height: Int) = apply { this.height = height }

            var height: Int?
            get() = root["height"].asInt
            set(value) {
                requireNotNull(value)
                require(value <= 255) { "Height can't be higher than 255! Found $height" }
                require(value >= 0) { "Height can't be smaller than 0! Found $height" }
                this.root.addProperty("height", height)
            }

            /**
             * Set the block of the layer.
             * @param blockId
             * @return
             */
            fun block(blockId: String): LayersBuilder {
                this.root.addProperty("block", blockId)
                return this
            }

            var block: Block?
            get() = root["block"].asId?.asBlock
            set(value) = root.addProperty("block", value?.id)
        }

        init {
            this.root.add("settings", JsonObject())
        }
    }
}