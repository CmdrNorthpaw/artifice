package com.swordglowsblue.artifice.api.builder.data.dimension

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder
import com.swordglowsblue.artifice.api.builder.data.dimension.BiomeSourceBuilder.*
import net.minecraft.util.Identifier
import java.util.function.Function

open class ChunkGeneratorTypeBuilder (type: Identifier) :
    TypedJsonBuilder<JsonObject>(JsonObject(), Function { j: JsonObject -> j })
{
    init {
        root.addProperty("type", type.toString())
    }

    /**
     * Set the type (ID) of the Chunk Generator.
     * @param type
     * @param <T>
     * @return
    </T> */

    /**
     * Set the biome Source.
     * @param instance
     * @param builder
     * @param <T>
     * @return
    </T> */
    fun <T : BiomeSourceBuilder<T>> biomeSource(
        instance: T,
        builder: T.() -> Unit
    ): ChunkGeneratorTypeBuilder {
        with("biome_source", { JsonObject() }) { biomeSource: JsonObject ->
            instance.apply(builder).buildTo(biomeSource)
        }
        return this
    }

    class NoiseChunkGeneratorTypeBuilder : ChunkGeneratorTypeBuilder(Identifier("noise")) {
        /**
         * Set a seed specially for this dimension.
         * @param seed
         * @return
         */
        fun seed(seed: Int): NoiseChunkGeneratorTypeBuilder {
            this.root.addProperty("seed", seed)
            return this
        }

        @Deprecated("use noiseSettings instead.")
        fun presetSettings(presetId: String): NoiseChunkGeneratorTypeBuilder {
            noiseSettings(presetId)
            return this
        }

        /**
         * Set Noise Settings to Id.
         * @param noiseSettingsID
         * @return
         */
        fun noiseSettings(noiseSettingsID: String): NoiseChunkGeneratorTypeBuilder {
            this.root.addProperty("settings", noiseSettingsID)
            return this
        }

        /**
         * Build a vanilla layered biome source.
         * @param biomeSourceBuilder
         * @return
         */
        fun vanillaLayeredBiomeSource(
            biomeSourceBuilder: VanillaLayeredBiomeSourceBuilder.() -> Unit
        ): NoiseChunkGeneratorTypeBuilder {
            biomeSource(VanillaLayeredBiomeSourceBuilder(), biomeSourceBuilder)
            return this
        }

        /**
         * Build a multi-noise biome source.
         * @param biomeSourceBuilder
         * @return
         */
        fun multiNoiseBiomeSource(biomeSourceBuilder: MultiNoiseBiomeSourceBuilder.() -> Unit): NoiseChunkGeneratorTypeBuilder {
            biomeSource(MultiNoiseBiomeSourceBuilder(), biomeSourceBuilder)
            return this
        }

        /**
         * Build a checkerboard biome source.
         * @param biomeSourceBuilder
         * @return
         */
        fun checkerboardBiomeSource(biomeSourceBuilder: CheckerboardBiomeSourceBuilder.() -> Unit)
        : NoiseChunkGeneratorTypeBuilder
        {
            biomeSource(CheckerboardBiomeSourceBuilder(), biomeSourceBuilder)
            return this
        }

        /**
         * Build a fixed biome source.
         * @param biomeSourceBuilder
         * @return
         */
        fun fixedBiomeSource(biomeSourceBuilder: FixedBiomeSourceBuilder.() -> Unit): NoiseChunkGeneratorTypeBuilder {
            biomeSource(FixedBiomeSourceBuilder(), biomeSourceBuilder)
            return this
        }

        /**
         * Build a simple biome source.
         * @param id
         * @return
         */
        fun simpleBiomeSource(id: String): NoiseChunkGeneratorTypeBuilder {
            this.root.addProperty("biome_source", id)
            return this
        }


    }

    class FlatChunkGeneratorTypeBuilder : ChunkGeneratorTypeBuilder(Identifier("flat")) {
        /**
         * Build a structure manager.
         * @param structureManagerBuilder
         * @return
         */
        fun structureManager(structureManagerBuilder: StructureManagerBuilder.() -> Unit): FlatChunkGeneratorTypeBuilder {
            with(root.getAsJsonObject("settings"), "structures", { JsonObject() }) { jsonObject: JsonObject ->
                StructureManagerBuilder().apply(structureManagerBuilder).buildTo(jsonObject)
            }
            return this
        }

        /**
         * Set the biome to biomeId.
         * @param biomeId
         * @return
         */
        fun biome(biomeId: String): FlatChunkGeneratorTypeBuilder {
            this.root.getAsJsonObject("settings").addProperty("biome", biomeId)
            return this
        }

        fun lakes(lakes: Boolean): FlatChunkGeneratorTypeBuilder {
            this.root.getAsJsonObject("settings").addProperty("lakes", lakes)
            return this
        }

        fun features(features: Boolean): FlatChunkGeneratorTypeBuilder {
            this.root.getAsJsonObject("settings").addProperty("features", features)
            return this
        }

        /**
         * Add a block layer.
         * @param layersBuilder
         * @return
         */
        fun addLayer(layersBuilder: LayersBuilder.() -> Unit): FlatChunkGeneratorTypeBuilder {
            with(
                root.getAsJsonObject("settings"),
                "layers",
                { JsonArray() }) { jsonElements: JsonArray ->
                jsonElements.add(
                    LayersBuilder().apply(layersBuilder).buildTo(JsonObject())
                )
            }
            return this
        }

        class LayersBuilder : TypedJsonBuilder<JsonObject>(JsonObject(), Function { j: JsonObject -> j }) {
            /**
             * Set the height of the layer.
             * @param height
             * @return
             */
            fun height(height: Int): LayersBuilder {
                require(height <= 255) { "Height can't be higher than 255! Found $height" }
                require(height >= 0) { "Height can't be smaller than 0! Found $height" }
                this.root.addProperty("height", height)
                return this
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
        }

        init {
            this.root.add("settings", JsonObject())
        }
    }
}