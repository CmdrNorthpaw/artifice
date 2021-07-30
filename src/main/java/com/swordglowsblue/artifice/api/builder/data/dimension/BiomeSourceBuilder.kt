package com.swordglowsblue.artifice.api.builder.data.dimension

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder
import net.minecraft.util.Identifier
import net.minecraft.world.biome.Biome
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
         * @param use
         * @return
         */
        fun largeBiomes(use: Boolean): VanillaLayeredBiomeSourceBuilder { this.largeBiomes = largeBiomes; return this }

        var largeBiomes
            get() = root.get("large_biomes").asBoolean
            set(value) = root.addProperty("large_biomes", value)

        var legacyBiomeInitLayer: Boolean
            get() = root.get("legacy_biome_init_layer").asBoolean
            set(value) = this.root.addProperty("legacy_biome_init_layer", value)

        fun legacyBiomeInitLayer(legacyBiomeInitLayer: Boolean): VanillaLayeredBiomeSourceBuilder {
            this.legacyBiomeInitLayer = legacyBiomeInitLayer
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
         */
        var preset: String
            get() = root.get("preset").asString
            set(value) = root.addProperty("preset", value)

        fun preset(preset: String): MultiNoiseBiomeSourceBuilder {
            this.preset = preset
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

            var biome: String
                get() = root.get("biome").asString
                set(value) = root.addProperty("biome", value)

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
            fun altitude(altitude: Float): BiomeParametersBuilder { this.altitude = altitude; return this }

            var altitude: Float
            get() = root.get("altitude").asFloat
            set(value) {
                require(altitude <= 2.0f) { "altitude can't be higher than 2.0F! Found $altitude" }
                require(altitude >= -2.0f) { "altitude can't be smaller than 2.0F! Found $altitude" }
                this.root.addProperty("altitude", value)
            }

            /**
             * @param weirdness
             * @return
             */
            fun weirdness(weirdness: Float): BiomeParametersBuilder { this.weirdness = weirdness; return this }

            var weirdness: Float
            get() = root.get("weirdness").asFloat
            set(value) {
                require(weirdness <= 2.0f) { "weirdness can't be higher than 2.0F! Found $weirdness" }
                require(weirdness >= -2.0f) { "weirdness can't be smaller than 2.0F! Found $weirdness" }
                this.root.addProperty("weirdness", value)
            }

            /**
             * @param offset
             * @return
             */
            var offset: Float
            get() = root.get("offset").asFloat
            set(value) {
                require(offset <= 1.0f) { "offset can't be higher than 1.0F! Found $offset" }
                require(offset >= 0.0f) { "offset can't be smaller than 0.0F! Found $offset" }
                this.root.addProperty("offset", value)
            }

            /**
             * @param temperature
             * @return
             */
            var temperature: Float
            get() = root.get("temperature").asFloat
            set(value) {
                require(temperature <= 2.0f) { "temperature can't be higher than 2.0F! Found $temperature" }
                require(temperature >= -2.0f) { "temperature can't be smaller than 2.0F! Found $temperature" }
                this.root.addProperty("temperature", value)
            }

            /**
             * @param humidity
             * @return
             */
            var humidity: Float
            get() = root.get("humidity").asFloat
            set(value) {
                require(humidity <= 2.0f) { "humidity can't be higher than 2.0F! Found $humidity" }
                require(humidity >= -2.0f) { "humidity can't be smaller than 2.0F! Found $humidity" }
                this.root.addProperty("humidity", value)
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

            var firstOctave: Int
            get() = root.get("firstOctave").asInt
            set(value) = root.addProperty("firstOctave", value)

            /**
             * @param amplitudes the amplitudes you want
             * @return this
             */
            fun amplitudes(vararg amplitudes: Float): NoiseSettings {
                this.amplitudes = amplitudes
                return this
            }

            var amplitudes: FloatArray
            get() = root.get("amplitudes").asJsonArray.map { it.asFloat }.toFloatArray()
            set(value) { jsonArray("amplitudes") {
                value.forEach { add(it) }
            } }
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

        var scale: Int
        get() = this.root.get("scale").asInt
        set(value) = this.root.addProperty("scale", value)

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

        var biome: String
        get() = root.get("biome").asString
        set(value) = root.addProperty("biome", value)
    }
}