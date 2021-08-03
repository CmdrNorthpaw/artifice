package com.swordglowsblue.artifice.api.builder.data.dimension

import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder
import com.swordglowsblue.artifice.api.dsl.ArtificeDsl
import com.swordglowsblue.artifice.api.util.Builder
import java.util.function.Function
import kotlin.jvm.Throws

@ArtificeDsl
class NoiseConfigBuilder : TypedJsonBuilder<JsonObject>(JsonObject(), { j: JsonObject -> j }) {
    fun height(height: Int) = apply { this.height = height }
    
    var height: Int?
    get() = root["height"].asInt
    set(value) = root.addProperty("height", value)

    fun minimumY(minimumY: Int): NoiseConfigBuilder {
        this.root.addProperty("min_y", minimumY)
        return this
    }
    
    var minimumY: Int?
    get() = root["min_y"].asInt
    set(value) = root.addProperty("min_y", value)

    fun horizontalSize(size: Int) = apply { this.horizontalSize = size }

    @set:OptIn(DangerousNoise::class)
    var horizontalSize: Int?
    get() = root["size_horizontal"].asInt
    set(value) {
        require(value != 3) { "value should not be 3! See https://gist.github.com/misode/b83bfe4964e6bf53b2dd31b22ee94157 for information of why it should not be 3" }
        dangerousHorizontalSize = value
    }

    @RequiresOptIn
    @Target(AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.FUNCTION)
    annotation class DangerousNoise

    @DangerousNoise
    fun dangerousHorizontalSize(noise: Int) = apply { this.dangerousHorizontalSize = noise }

    @set:DangerousNoise
    var dangerousHorizontalSize: Int?
    get() = root["size_horizontal"].asInt
    set(value) {
        requireNotNull(value)
        require(value <= 4) { "value can't be higher than 4! Found $value" }
        require(value >= 1) { "value can't be smaller than 1! Found $value" }
        root.addProperty("size_horizontal", value)
    }

    fun sizeVertical(size: Int) = apply { this.verticalSize = size }

    var verticalSize: Int?
    get() = root["size_vertical"].asInt
    set(value) {
        requireNotNull(value)
        require(value <= 4) { "sizeVertical can't be higher than 4! Found $value" }
        require(value >= 1) { "sizeVertical can't be smaller than 1! Found $value" }
        this.root.addProperty("size_vertical", value)
    }

    fun densityFactor(densityFactor: Double): NoiseConfigBuilder {
        this.root.addProperty("density_factor", densityFactor)
        return this
    }

    var densityFactor: Double?
    get() = root["density_factor"].asDouble
    set(value) = root.addProperty("density_factor", value)

    @Deprecated("use the double version instead",
    replaceWith = ReplaceWith("densityOffset(densityOffset: Double)")
    )
    fun densityOffset(densityOffset: Int): NoiseConfigBuilder {
        return this.densityOffset(densityOffset.toDouble())
    }

    fun densityOffset(densityOffset: Double) = apply { this.densityOffset = densityOffset }

    var densityOffset: Double?
    get() = root["density_offset"].asDouble
    set(value) = root.addProperty("density_offset", value)

    fun simplexSurfaceNoise(simplexSurfaceNoise: Boolean) = apply { this.simplexSurfaceNoise = simplexSurfaceNoise }

    var simplexSurfaceNoise: Boolean?
    get() = root["simplex_surface_noise"].asBoolean
    set(value) = root.addProperty("simplex_surface_noide", value)

    fun randomDensityOffset(randomDensityOffset: Boolean) = apply { this.randomDensityOffset = randomDensityOffset }

    var randomDensityOffset: Boolean?
    get() = root["random_density_offset"].asBoolean
    set(value) = root.addProperty("random_density_offset", value)

    fun islandNoiseOverride(islandNoiseOverride: Boolean) = apply { this.islandNoiseOverride = islandNoiseOverride }

    var islandNoiseOverride: Boolean?
    get() = root["island_noise_override"].asBoolean
    set(value) = root.addProperty("island_noise_override", value)

    fun amplified(amplified: Boolean) = apply { this.isAmplified = amplified }

    var isAmplified: Boolean?
    get() = root["amplified"].asBoolean
    set(value) = root.addProperty("amplified", value)

    /**
     * Build noise sampling config.
     */
    fun sampling(noiseSamplingConfigBuilder: Builder<NoiseSamplingConfigBuilder>) = apply {
        with("sampling", { JsonObject() }) { jsonObject: JsonObject ->
            NoiseSamplingConfigBuilder().apply(noiseSamplingConfigBuilder).buildTo(jsonObject)
        }
    }

    /**
     * Build slide config.
     */
    private fun slideConfig(id: String, processor: Builder<SlideConfigBuilder>) = apply {
        with(id, { JsonObject() }) { jsonObject: JsonObject ->
            SlideConfigBuilder().apply(processor).buildTo(jsonObject)
        }
    }

    /**
     * Build top slide.
     */
    fun topSlide(slideConfigBuilder: Builder<SlideConfigBuilder>): NoiseConfigBuilder {
        return slideConfig("top_slide", slideConfigBuilder)
    }

    /**
     * Build bottom slide.
     */
    fun bottomSlide(slideConfigBuilder: Builder<SlideConfigBuilder>): NoiseConfigBuilder {
        return slideConfig("bottom_slide", slideConfigBuilder)
    }

    @ArtificeDsl
    class NoiseSamplingConfigBuilder : TypedJsonBuilder<JsonObject>(JsonObject(), { j: JsonObject -> j }) {
        @Throws(IllegalArgumentException::class)
        private fun requireScale(value: Double?) = value?.also { value ->
            require(value <= 1000.0) { "value can't be higher than 1000.0D! Found $value" }
            require(value >= 0.001) { "value can't be smaller than 0.001D! Found $value" }
        }

        fun xzScale(xzScale: Double) = apply { this.xzScale = xzScale }

        var xzScale: Double?
        get() = root["xz_scale"].asDouble
        set(value) = root.addProperty("xz_scale", requireScale(value))

        fun yScale(yScale: Double) = apply { this.yScale = yScale }

        var yScale: Double?
        get() = root["y_scale"].asDouble
        set(value) = root.addProperty("y_scale", requireScale(value))

        fun xzFactor(xzFactor: Double) = apply { this.xzFactor = xzFactor }

        var xzFactor: Double?
        get() = root["y_scale"].asDouble
        set(value) = root.addProperty("y_scale", requireScale(value))

        fun yFactor(yFactor: Double): NoiseSamplingConfigBuilder {
            require(yFactor <= 1000.0) { "yFactor can't be higher than 1000.0D! Found $yFactor" }
            require(yFactor >= 0.001) { "yFactor can't be smaller than 0.001D! Found $yFactor" }
            this.root.addProperty("y_factor", yFactor)
            return this
        }

        var yFactor: Double?
        get() = root["y_factor"].asDouble
        set(value) = root.addProperty("y_factor", requireScale(value))
    }

    @ArtificeDsl
    class SlideConfigBuilder : TypedJsonBuilder<JsonObject>(JsonObject(), { j: JsonObject -> j }) {
        fun offset(offset: Int) = apply { this.offset = offset }

        var offset: Int?
        get() = root["offset"].asInt
        set(value) = root.addProperty("offset", value)

        fun size(size: Int) = apply { this.size = size }

        var size: Int?
        get() = root["size"].asInt
        set(value) {
            requireNotNull(value)
            require(value <= 255) { "size can't be higher than 255! Found $value" }
            require(value >= 0) { "size can't be smaller than 0! Found $value" }
            this.root.addProperty("size", value)
        }

        fun target(target: Int) = apply { this.target = target }

        var target: Int?
        get() = root["target"].asInt
        set(value) = root.addProperty("target", value)
    }
}