package com.swordglowsblue.artifice.api.builder.data.dimension

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder
import com.swordglowsblue.artifice.api.dsl.ArtificeDsl
import com.swordglowsblue.artifice.api.util.Builder
import com.swordglowsblue.artifice.api.util.IdUtils.asId
import net.minecraft.util.Identifier
import java.util.function.Function
import kotlin.jvm.Throws

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

    var seed: Int?
    get() = root["seed"]?.asInt
    set(value) = root.addProperty("seed", value)

    @ArtificeDsl
    class VanillaLayeredBiomeSourceBuilder : BiomeSourceBuilder<VanillaLayeredBiomeSourceBuilder>(
        Identifier("vanilla_layered")
    ) {
        override val me: VanillaLayeredBiomeSourceBuilder
            get() = this

        /**
         * @param largeBiomes
         * @return
         */
        fun largeBiomes(largeBiomes: Boolean) = apply { this.largeBiomes = largeBiomes }

        var largeBiomes: Boolean?
        get() = root["large_biomes"]?.asBoolean
        set(value) = root.addProperty("large_biomes", value)

        /**
         * @param legacyBiomeInitLayer
         * @return
         */
        fun legacyBiomeInitLayer(legacyBiomeInitLayer: Boolean) = apply { this.legacyBiomeInitLayer = legacyBiomeInitLayer }

        var legacyBiomeInitLayer: Boolean?
        get() = root["legacy_biome_init_layer"].asBoolean
        set(value) = root.addProperty("legacy_biome_init_layer", value)
    }

    @ArtificeDsl
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

        var preset: String?
        get() = root["preset"]?.asString
        set(value) = root.addProperty("preset", value)

        /**
         * Add biome.
         * @param biomeBuilder
         * @return
         */
        fun addBiome(biomeBuilder: Builder<BiomeBuilder>) = apply {
            with("biomes", { JsonArray() }) { biomeArray: JsonArray ->
                biomeArray.add(
                    BiomeBuilder().apply(biomeBuilder).buildTo(JsonObject())
                )
            }
        }

        @ArtificeDsl
        class BiomeBuilder : TypedJsonBuilder<JsonObject>(JsonObject(), { j: JsonObject -> j }) {
            /**
             * Set the biome ID.
             * @param id
             * @return
             */
            fun id(id: Identifier) = apply { this.id = id }

            var id: Identifier?
            get() = root["biome"].asId
            set(value) = root.addProperty("biome", value?.toString())

            /**
             * Build biome parameters.
             * @param biomeSettingsBuilder
             * @return
             */
            fun parameters(biomeSettingsBuilder: Builder<BiomeParametersBuilder>) = apply {
                with("parameters", { JsonObject() }) { biomeSettings: JsonObject ->
                    BiomeParametersBuilder().apply(biomeSettingsBuilder).buildTo(biomeSettings)
                }
            }
        }

        @ArtificeDsl
        class BiomeParametersBuilder : TypedJsonBuilder<JsonObject>(JsonObject(), { j: JsonObject -> j }) {
            @Throws(IllegalArgumentException::class)
            fun require2F(float: Float?, fieldName: String) = float?.apply {
                require(float <= 2.0f) { "altitude can't be higher than     2! Found $float" }
                require(float >= -2.0f) { "$fieldName can't be less than -2! Found $float" }
            }

            /**
             * @param altitude
             * @return
             */
            fun altitude(altitude: Float) = apply { this.altitude = altitude }

            var altitude: Float?
            get() = root["altitude"]?.asFloat
            set(value) = root.addProperty("altitude", require2F(value, "altitude"))

            /**
             * @param weirdness
             * @return
             */
            fun weirdness(weirdness: Float) = apply { this.weirdness = weirdness }

            var weirdness: Float?
            get() = root["weirdness"]?.asFloat
            set(value) = root.addProperty("weirdness", require2F(value, "weirdness"))

            /**
             * @param offset
             * @return
             */
            fun offset(offset: Float) = apply { this.offset = offset }

            var offset: Float?
            get() = root["offset"]?.asFloat
            set(value) = root.addProperty("offset", require2F(value, "offset"))

            /**
             * @param temperature
             * @return
             */
            fun temperature(temperature: Float) = apply { this.temperature = temperature }

            var temperature: Float?
            get() = root["temperature"]?.asFloat
            set(value) = root.addProperty("temperature", require2F(value, "temperature"))

            /**
             * @param humidity
             * @return
             */
            fun humidity(humidity: Float) = apply { this.humidity = humidity }

            var humidity: Float?
            get() = root["humidity"]?.asFloat
            set(value) = root.addProperty("humidity", require2F(value, "humidity"))
        }

        /**
         * @param noiseSettings
         * @return this
         */
        fun altitudeNoise(noiseSettings: NoiseSettings.() -> Unit) = apply {
            with("altitude_noise", { JsonObject() }) { jsonObject: JsonObject ->
                NoiseSettings().apply(noiseSettings).buildTo(jsonObject)
            }
            return this
        }

        /**
         * @param noiseSettings
         * @return this
         */
        fun weirdnessNoise(noiseSettings: NoiseSettings.() -> Unit) = apply {
            with("weirdness_noise", { JsonObject() }) { jsonObject: JsonObject ->
                NoiseSettings().apply(noiseSettings).buildTo(jsonObject)
            }
        }

        /**
         * @param noiseSettings
         * @return this
         */
        fun temperatureNoise(noiseSettings: NoiseSettings.() -> Unit) = apply {
            with("temperature_noise", { JsonObject() }) { jsonObject: JsonObject ->
                NoiseSettings().apply(noiseSettings).buildTo(jsonObject)
            }
        }

        /**
         * @param noiseSettings
         * @return this
         */
        fun humidityNoise(noiseSettings: NoiseSettings.() -> Unit) = apply {
            with("humidity_noise", { JsonObject() }) { jsonObject: JsonObject ->
                NoiseSettings().apply(noiseSettings).buildTo(jsonObject)
            }
        }

        @ArtificeDsl
        class NoiseSettings : TypedJsonBuilder<JsonObject>(JsonObject(), { j: JsonObject -> j }) {
            /**
             * Changes how much detail the noise of the respective value has
             * @param octave how much detail the noise of the respective value has
             * @return this
             */
            fun firstOctave(octave: Int) = apply { firstOctave = octave }

            var firstOctave: Int?
            get() = root["firstOctave"]?.asInt
            set(value) = root.addProperty("firstOctave", value)

            /**
             * @param amplitudes the amplitudes you want
             * @return this
             */
            fun amplitudes(vararg amplitudes: Float) = apply {
                jsonArray("amplitudes") {
                    amplitudes.forEach { add(it) }
                }
            }
        }

        class AmplitudesBuilder private constructor() :
            TypedJsonBuilder<JsonObject>(JsonObject(), { j: JsonObject -> j }) {
            /**
             * @param amplitude idk
             * @return
             */
            fun amplitude(amplitude: Float) = apply { this.amplitude = amplitude }

            var amplitude: Float?
            get() = root["amplitude"]?.asFloat
            set(value) = root.addProperty("amplitude", value)
        }
    }

    @ArtificeDsl
    class CheckerboardBiomeSourceBuilder :
        BiomeSourceBuilder<CheckerboardBiomeSourceBuilder>(Identifier("checkerboard")) {
        override val me: CheckerboardBiomeSourceBuilder
            get() = this

        /**
         * @param scale
         * @return
         */
        fun scale(scale: Int): CheckerboardBiomeSourceBuilder {
            return this
        }

        var scale: Int?
        get() = root["scale"].asInt
        set(value) {
            requireNotNull(value)
            require(value <= 62) { "Scale can't be higher than 62! Found $value" }
            require(value >= 0) { "Scale can't be smaller than 0! Found $value" }
            this.root.addProperty("scale", value)
        }

        /**
         * Add biome.
         * @param biomeId
         * @return
         */
        fun addBiome(biomeId: Identifier): CheckerboardBiomeSourceBuilder {
            this.root.getAsJsonArray("biomes").add(biomeId.toString())
            return this
        }

        init {
            this.root.add("biomes", JsonArray())
        }
    }

    @ArtificeDsl
    class FixedBiomeSourceBuilder : BiomeSourceBuilder<FixedBiomeSourceBuilder>(Identifier("fixed")) {
        override val me: FixedBiomeSourceBuilder
            get() = this

        /**
         * Set biome ID.
         * @param biomeId
         * @return
         */
        fun id(biomeId: Identifier) = apply { id = biomeId }

        var id: Identifier?
        get() = root["biome"].asId
        set(value) = root.addProperty("id", value.toString())
    }
}