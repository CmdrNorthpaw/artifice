package com.swordglowsblue.artifice.api.builder.data.dimension

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder
import net.minecraft.util.Identifier
import java.util.function.Function

sealed class BiomeSourceBuilder<T: BiomeSourceBuilder<T>>(
    type: Identifier
) : TypedJsonBuilder<JsonObject>(JsonObject(), { j: JsonObject -> j }) {
    protected abstract val me: T

    init {
        root.addProperty("type", type.toString())
    }

    /**
     * Set the seed of the biome source.
     * @param seed
     * @param <T>
     * @return
    </T> */
    fun seed(seed: Int): T {
        this.root.addProperty("seed", seed)
        return me
    }

    /**
     * Set the type of the biome source.
     * @param type
     * @param <T>
     * @return
    </T> */
    fun type(type: String): T {
        this.root.addProperty("type", type)
        return me
    }

    class VanillaLayeredBiomeSourceBuilder : BiomeSourceBuilder<VanillaLayeredBiomeSourceBuilder>(
        Identifier("vanilla_layered")
    ) {
        override val me: VanillaLayeredBiomeSourceBuilder
            get() = this

        /**
         * @param largeBiomes
         * @return
         */
        fun largeBiomes(largeBiomes: Boolean): VanillaLayeredBiomeSourceBuilder {
            this.root.addProperty("large_biomes", largeBiomes)
            return this
        }

        /**
         * @param legacyBiomeInitLayer
         * @return
         */
        fun legacyBiomeInitLayer(legacyBiomeInitLayer: Boolean): VanillaLayeredBiomeSourceBuilder {
            this.root.addProperty("legacy_biome_init_layer", legacyBiomeInitLayer)
            return this
        }
    }

    class MultiNoiseBiomeSourceBuilder : BiomeSourceBuilder<MultiNoiseBiomeSourceBuilder>(
        Identifier("multi_noise")
    ) {
        override val me: MultiNoiseBiomeSourceBuilder
            get() = this

        /**
         * Set the preset.
         * @param preset
         * @return
         */
        fun preset(preset: String): MultiNoiseBiomeSourceBuilder {
            this.root.addProperty("preset", preset)
            return this
        }

        /**
         * Add biome.
         * @param biomeBuilder
         * @return
         */
        fun addBiome(biomeBuilder: BiomeBuilder.() -> Unit): MultiNoiseBiomeSourceBuilder {
            with(
                "biomes",
                { JsonArray() }) { biomeArray: JsonArray ->
                biomeArray.add(
                    BiomeBuilder().apply(biomeBuilder).buildTo(JsonObject())
                )
            }
            return this
        }

        class BiomeBuilder : TypedJsonBuilder<JsonObject>(JsonObject(), { j: JsonObject -> j }) {
            /**
             * Set the biome ID.
             * @param id
             * @return
             */
            fun biome(id: String): BiomeBuilder {
                this.root.addProperty("biome", id)
                return this
            }

            /**
             * Build biome parameters.
             * @param biomeSettingsBuilder
             * @return
             */
            fun parameters(biomeSettingsBuilder: BiomeParametersBuilder.() -> Unit): BiomeBuilder {
                with("parameters", { JsonObject() }) { biomeSettings: JsonObject ->
                    BiomeParametersBuilder().apply(biomeSettingsBuilder).buildTo(biomeSettings)
                }
                return this
            }
        }

        class BiomeParametersBuilder : TypedJsonBuilder<JsonObject>(JsonObject(), { j: JsonObject -> j }) {
            /**
             * @param altitude
             * @return
             */
            fun altitude(altitude: Float): BiomeParametersBuilder {
                require(altitude <= 2.0f) { "altitude can't be higher than 2.0F! Found $altitude" }
                require(altitude >= -2.0f) { "altitude can't be smaller than 2.0F! Found $altitude" }
                this.root.addProperty("altitude", altitude)
                return this
            }

            /**
             * @param weirdness
             * @return
             */
            fun weirdness(weirdness: Float): BiomeParametersBuilder {
                require(weirdness <= 2.0f) { "weirdness can't be higher than 2.0F! Found $weirdness" }
                require(weirdness >= -2.0f) { "weirdness can't be smaller than 2.0F! Found $weirdness" }
                this.root.addProperty("weirdness", weirdness)
                return this
            }

            /**
             * @param offset
             * @return
             */
            fun offset(offset: Float): BiomeParametersBuilder {
                require(offset <= 1.0f) { "offset can't be higher than 1.0F! Found $offset" }
                require(offset >= 0.0f) { "offset can't be smaller than 0.0F! Found $offset" }
                this.root.addProperty("offset", offset)
                return this
            }

            /**
             * @param temperature
             * @return
             */
            fun temperature(temperature: Float): BiomeParametersBuilder {
                require(temperature <= 2.0f) { "temperature can't be higher than 2.0F! Found $temperature" }
                require(temperature >= -2.0f) { "temperature can't be smaller than 2.0F! Found $temperature" }
                this.root.addProperty("temperature", temperature)
                return this
            }

            /**
             * @param humidity
             * @return
             */
            fun humidity(humidity: Float): BiomeParametersBuilder {
                require(humidity <= 2.0f) { "humidity can't be higher than 2.0F! Found $humidity" }
                require(humidity >= -2.0f) { "humidity can't be smaller than 2.0F! Found $humidity" }
                this.root.addProperty("humidity", humidity)
                return this
            }
        }

        /**
         * @param noiseSettings
         * @return this
         */
        fun altitudeNoise(noiseSettings: NoiseSettings.() -> Unit): MultiNoiseBiomeSourceBuilder {
            with("altitude_noise", { JsonObject() }) { jsonObject: JsonObject ->
                NoiseSettings().apply(noiseSettings).buildTo(jsonObject)
            }
            return this
        }

        /**
         * @param noiseSettings
         * @return this
         */
        fun weirdnessNoise(noiseSettings: NoiseSettings.() -> Unit): MultiNoiseBiomeSourceBuilder {
            with("weirdness_noise", { JsonObject() }) { jsonObject: JsonObject ->
                NoiseSettings().apply(noiseSettings).buildTo(jsonObject)
            }
            return this
        }

        /**
         * @param noiseSettings
         * @return this
         */
        fun temperatureNoise(noiseSettings: NoiseSettings.() -> Unit): MultiNoiseBiomeSourceBuilder {
            with("temperature_noise", { JsonObject() }) { jsonObject: JsonObject ->
                NoiseSettings().apply(noiseSettings).buildTo(jsonObject)
            }
            return this
        }

        /**
         * @param noiseSettings
         * @return this
         */
        fun humidityNoise(noiseSettings: NoiseSettings.() -> Unit): MultiNoiseBiomeSourceBuilder {
            with("humidity_noise", { JsonObject() }) { jsonObject: JsonObject ->
                NoiseSettings().apply(noiseSettings).buildTo(jsonObject)
            }
            return this
        }

        class NoiseSettings : TypedJsonBuilder<JsonObject>(JsonObject(), { j: JsonObject -> j }) {
            /**
             * Changes how much detail the noise of the respective value has
             * @param octave how much detail the noise of the respective value has
             * @return this
             */
            fun firstOctave(octave: Int): NoiseSettings {
                this.root.addProperty("firstOctave", octave)
                return this
            }

            /**
             * @param amplitudes the amplitudes you want
             * @return this
             */
            fun amplitudes(vararg amplitudes: Float): NoiseSettings {
                jsonArray("amplitudes") {
                    amplitudes.forEach { add(it) }
                }
                return this
            }
        }

        class AmplitudesBuilder private constructor() :
            TypedJsonBuilder<JsonObject>(JsonObject(), { j: JsonObject -> j }) {
            /**
             * @param amplitude idk
             * @return
             */
            fun amplitude(amplitude: Float): AmplitudesBuilder {
                this.root.addProperty("altitude", amplitude)
                return this
            }
        }
    }

    class CheckerboardBiomeSourceBuilder : BiomeSourceBuilder<CheckerboardBiomeSourceBuilder>(Identifier("checkerboard")) {
        override val me: CheckerboardBiomeSourceBuilder
            get() = this

        /**
         * @param scale
         * @return
         */
        fun scale(scale: Int): CheckerboardBiomeSourceBuilder {
            require(scale <= 62) { "Scale can't be higher than 62! Found $scale" }
            require(scale >= 0) { "Scale can't be smaller than 0! Found $scale" }
            this.root.addProperty("scale", scale)
            return this
        }

        /**
         * Add biome.
         * @param biomeId
         * @return
         */
        fun addBiome(biomeId: String): CheckerboardBiomeSourceBuilder {
            this.root.getAsJsonArray("biomes").add(biomeId)
            return this
        }

        init {
            this.root.add("biomes", JsonArray())
        }
    }

    class FixedBiomeSourceBuilder : BiomeSourceBuilder<FixedBiomeSourceBuilder>(Identifier("fixed")) {
        override val me: FixedBiomeSourceBuilder
            get() = this

        /**
         * Set biome ID.
         * @param biomeId
         * @return
         */
        fun biome(biomeId: String): FixedBiomeSourceBuilder {
            this.root.addProperty("biome", biomeId)
            return this
        }
    }
}