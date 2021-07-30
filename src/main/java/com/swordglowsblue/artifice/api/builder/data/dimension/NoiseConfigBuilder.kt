package com.swordglowsblue.artifice.api.builder.data.dimension

import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder
import com.swordglowsblue.artifice.api.util.Builder
import java.util.function.Function

class NoiseConfigBuilder : TypedJsonBuilder<JsonObject>(JsonObject(), { j: JsonObject -> j }) {
    fun height(height: Int): NoiseConfigBuilder {
        this.root.addProperty("height", height)
        return this
    }

    var height: Int
    get() = root["height"].asInt
    set(value) = root.addProperty("height", value)

    fun minimumY(minimumY: Int): NoiseConfigBuilder {
        this.root.addProperty("min_y", minimumY)
        return this
    }

    var minimumY: Int
    get() = root["min_y"].asInt
    set(value) = root.addProperty("min_y", value)

    fun horizontalSize(sizeHorizontal: Int): NoiseConfigBuilder {
        require(sizeHorizontal <= 4) { "sizeHorizontal can't be higher than 4! Found $sizeHorizontal" }
        require(sizeHorizontal >= 1) { "sizeHorizontal can't be smaller than 1! Found $sizeHorizontal" }
        require(sizeHorizontal != 3) { "sizeHorizontal should not be 3! See https://gist.github.com/misode/b83bfe4964e6bf53b2dd31b22ee94157 for information of why it should not be 3" }
        this.root.addProperty("size_horizontal", sizeHorizontal)
        return this
    }

    var horizontalSize: Int
    get() = root["size_horizontal"].asInt
    set(value) {
        require(value <= 4) { "value can't be higher than 4! Found $value" }
        require(value >= 1) { "value can't be smaller than 1! Found $value" }
        require(value != 3) { "value should not be 3! See https://gist.github.com/misode/b83bfe4964e6bf53b2dd31b22ee94157 for information of why it should not be 3" }
        this.root.addProperty("size_horizontal", value)
    }

    fun sizeVertical(sizeVertical: Int): NoiseConfigBuilder {
        require(sizeVertical <= 4) { "sizeVertical can't be higher than 4! Found $sizeVertical" }
        require(sizeVertical >= 1) { "sizeVertical can't be smaller than 1! Found $sizeVertical" }
        this.root.addProperty("size_vertical", sizeVertical)
        return this
    }

    var verticalSize: Int
    get() = root["size_vertical"].asInt
    set(value) = root.addProperty("size_vertical", value)

    fun densityFactor(densityFactor: Double): NoiseConfigBuilder {
        this.root.addProperty("density_factor", densityFactor)
        return this
    }

    var densityFactor: Double
    get() = root["density_factor"].asDouble
    set(value) = root.addProperty("density_factor", value)

    @Deprecated("use the double version instead",
        replaceWith = ReplaceWith("densityOffset(densityOffset: Double)")
    )
    fun densityOffset(densityOffset: Int): NoiseConfigBuilder {
        return this.densityOffset(densityOffset.toDouble())
    }

    fun densityOffset(densityOffset: Double): NoiseConfigBuilder {
        require(densityOffset <= 1) { "densityOffset can't be higher than 1! Found $densityOffset" }
        require(densityOffset >= -1) { "densityOffset can't be smaller than -1! Found $densityOffset" }
        this.root.addProperty("density_offset", densityOffset)
        return this
    }

    var densityOffset: Double
    get() = root["density_offset"].asDouble
    set(value) { densityOffset(value); Unit }

    fun simplexSurfaceNoise(simplexSurfaceNoise: Boolean): NoiseConfigBuilder {
        this.root.addProperty("simplex_surface_noise", simplexSurfaceNoise)
        return this
    }

    var simplexSurfaceNoise: Boolean
    get() = root["simplex_surface_noise"].asBoolean
    set(value) = root.addProperty("simplex_surface_noise", value)

    fun randomDensityOffset(randomDensityOffset: Boolean): NoiseConfigBuilder {
        this.root.addProperty("random_density_offset", randomDensityOffset)
        return this
    }

    var randomDensityOffset: Boolean
    get() = root["random_density_offset"].asBoolean
    set(value) = root.addProperty("random_density_offset", value)

    fun islandNoiseOverride(islandNoiseOverride: Boolean): NoiseConfigBuilder {
        this.root.addProperty("island_noise_override", islandNoiseOverride)
        return this
    }

    var islandNoiseOverride: Boolean
    get() = root["island_noise_override"].asBoolean
    set(value) = root.addProperty("island_noise_override", value)

    fun amplified(amplified: Boolean): NoiseConfigBuilder {
        this.root.addProperty("amplified", amplified)
        return this
    }

    var isAmplified: Boolean
    get() = root["amplified"].asBoolean
    set(value) = root.addProperty("amplified", value)

    /**
     * Build noise sampling config.
     */
    fun sampling(noiseSamplingConfigBuilder: Builder<NoiseSamplingConfigBuilder>): NoiseConfigBuilder {
        with("sampling", { JsonObject() }) { jsonObject: JsonObject ->
            NoiseSamplingConfigBuilder().apply(noiseSamplingConfigBuilder).buildTo(jsonObject)
        }
        return this
    }

    /**
     * Build slide config.
     */
    private fun slideConfig(
        id: String,
        processor: Builder<SlideConfigBuilder>
    ): NoiseConfigBuilder {
        with(id, { JsonObject() }) { jsonObject: JsonObject ->
            SlideConfigBuilder().apply(processor).buildTo(jsonObject)
        }
        return this
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

    class NoiseSamplingConfigBuilder : TypedJsonBuilder<JsonObject>(JsonObject(), { j: JsonObject -> j }) {
        fun xzScale(xzScale: Double): NoiseSamplingConfigBuilder {
            require(xzScale <= 1000.0) { "xzScale can't be higher than 1000.0D! Found $xzScale" }
            require(xzScale >= 0.001) { "xzScale can't be smaller than 0.001D! Found $xzScale" }
            this.root.addProperty("xz_scale", xzScale)
            return this
        }

        var xzScale: Double
        get() = root["xz_scale"].asDouble
        set(value) { xzScale(value); Unit }

        fun yScale(yScale: Double): NoiseSamplingConfigBuilder {
            require(yScale <= 1000.0) { "yScale can't be higher than 1000.0D! Found $yScale" }
            require(yScale >= 0.001) { "yScale can't be smaller than 0.001D! Found $yScale" }
            this.root.addProperty("y_scale", yScale)
            return this
        }

        var yScale: Double
        get() = root["y_scale"].asDouble
        set(value) { yScale(value) }

        fun xzFactor(xzFactor: Double): NoiseSamplingConfigBuilder {
            require(xzFactor <= 1000.0) { "xzFactor can't be higher than 1000.0D! Found $xzFactor" }
            require(xzFactor >= 0.001) { "xzFactor can't be smaller than 0.001D! Found $xzFactor" }
            this.root.addProperty("xz_factor", xzFactor)
            return this
        }

        var xzFactor: Double
        get() = root["xz_factor"].asDouble
        set(value) { xzFactor(value) }

        fun yFactor(yFactor: Double): NoiseSamplingConfigBuilder {
            require(yFactor <= 1000.0) { "yFactor can't be higher than 1000.0D! Found $yFactor" }
            require(yFactor >= 0.001) { "yFactor can't be smaller than 0.001D! Found $yFactor" }
            this.root.addProperty("y_factor", yFactor)
            return this
        }

        var yFactor: Double
        get() = root["y_factor"].asDouble
        set(value) { yFactor(value) }
    }

    class SlideConfigBuilder : TypedJsonBuilder<JsonObject>(JsonObject(), { j: JsonObject -> j }) {
        fun offset(offset: Int): SlideConfigBuilder {
            this.root.addProperty("offset", offset)
            return this
        }

        var offset: Int
        get() = root["offset"].asInt
        set(value) = root.addProperty("offset", value)

        fun size(size: Int): SlideConfigBuilder {
            require(size <= 255) { "size can't be higher than 255! Found $size" }
            require(size >= 0) { "size can't be smaller than 0! Found $size" }
            this.root.addProperty("size", size)
            return this
        }

        var size: Int
        get() = root["size"].asInt
        set(value) { size(value) }

        fun target(target: Int): SlideConfigBuilder {
            this.root.addProperty("target", target)
            return this
        }

        var target: Int
        get() = root["target"].asInt
        set(value) = root.addProperty("target", value)
    }
}