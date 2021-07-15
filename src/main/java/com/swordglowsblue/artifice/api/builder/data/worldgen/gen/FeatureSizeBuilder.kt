package com.swordglowsblue.artifice.api.builder.data.worldgen.gen

import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder
import net.minecraft.util.Identifier
import java.util.function.Function

sealed class FeatureSizeBuilder(
    type: Identifier
) : TypedJsonBuilder<JsonObject>(JsonObject(), Function { j: JsonObject -> j }) {

    @Suppress("UNCHECKED_CAST")
    fun <S : FeatureSizeBuilder> minClippedHeight(minClippedHeight: Int): S {
        require(minClippedHeight <= 80) { "minClippedHeight can't be higher than 80! Found $minClippedHeight" }
        require(minClippedHeight >= 0) { "minClippedHeight can't be higher than 0! Found $minClippedHeight" }
        this.root.addProperty("min_clipped_height", minClippedHeight)
        return this as S
    }

    class TwoLayersFeatureSizeBuilder : FeatureSizeBuilder(Identifier("two_layers_feature_size")) {
        fun limit(limit: Int): TwoLayersFeatureSizeBuilder {
            require(limit <= 81) { "limit can't be higher than 81! Found $limit" }
            require(limit >= 0) { "limit can't be higher than 0! Found $limit" }
            this.root.addProperty("limit", limit)
            return this
        }

        fun lowerSize(lowerSize: Int): TwoLayersFeatureSizeBuilder {
            require(lowerSize <= 16) { "lowerSize can't be higher than 16! Found $lowerSize" }
            require(lowerSize >= 0) { "lowerSize can't be higher than 0! Found $lowerSize" }
            this.root.addProperty("lower_size", lowerSize)
            return this
        }

        fun upperSize(upperSize: Int): TwoLayersFeatureSizeBuilder {
            require(upperSize <= 16) { "upperSize can't be higher than 16! Found $upperSize" }
            require(upperSize >= 0) { "upperSize can't be higher than 0! Found $upperSize" }
            this.root.addProperty("upper_size", upperSize)
            return this
        }

    }

    class ThreeLayersFeatureSizeBuilder : FeatureSizeBuilder(Identifier("three_layers_feature_size")) {
        fun limit(limit: Int): ThreeLayersFeatureSizeBuilder {
            require(limit <= 80) { "limit can't be higher than 80! Found $limit" }
            require(limit >= 0) { "limit can't be higher than 0! Found $limit" }
            this.root.addProperty("limit", limit)
            return this
        }

        fun upperLimit(upperLimit: Int): ThreeLayersFeatureSizeBuilder {
            require(upperLimit <= 80) { "upperLimit can't be higher than 80! Found $upperLimit" }
            require(upperLimit >= 0) { "upperLimit can't be higher than 0! Found $upperLimit" }
            this.root.addProperty("upper_limit", upperLimit)
            return this
        }

        fun lowerSize(lowerSize: Int): ThreeLayersFeatureSizeBuilder {
            require(lowerSize <= 16) { "lowerSize can't be higher than 16! Found $lowerSize" }
            require(lowerSize >= 0) { "lowerSize can't be higher than 0! Found $lowerSize" }
            this.root.addProperty("lower_size", lowerSize)
            return this
        }

        fun middleSize(middleSize: Int): ThreeLayersFeatureSizeBuilder {
            require(middleSize <= 16) { "middleSize can't be higher than 16! Found $middleSize" }
            require(middleSize >= 0) { "middleSize can't be higher than 0! Found $middleSize" }
            this.root.addProperty("middle_size", middleSize)
            return this
        }

        fun upperSize(upperSize: Int): ThreeLayersFeatureSizeBuilder {
            require(upperSize <= 16) { "upperSize can't be higher than 16! Found $upperSize" }
            require(upperSize >= 0) { "upperSize can't be higher than 0! Found $upperSize" }
            this.root.addProperty("upper_size", upperSize)
            return this
        }
    }

    init {
        root.addProperty("type", type.toString())
    }
}