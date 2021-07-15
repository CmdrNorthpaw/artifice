package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.decorator.config

import com.swordglowsblue.artifice.api.builder.data.worldgen.configured.decorator.config.DecoratorConfigBuilder
import com.swordglowsblue.artifice.api.builder.data.worldgen.UniformIntDistributionBuilder
import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder
import com.swordglowsblue.artifice.api.builder.data.worldgen.configured.decorator.config.RangeDecoratorConfigBuilder
import com.swordglowsblue.artifice.api.builder.data.worldgen.configured.decorator.config.ChanceDecoratorConfigBuilder
import com.swordglowsblue.artifice.api.builder.data.worldgen.configured.decorator.ConfiguredDecoratorBuilder
import com.swordglowsblue.artifice.api.builder.data.worldgen.configured.decorator.config.DecoratedDecoratorConfigBuilder
import com.swordglowsblue.artifice.api.builder.data.worldgen.configured.decorator.config.CountExtraDecoratorConfigBuilder
import com.swordglowsblue.artifice.api.builder.data.worldgen.configured.decorator.config.CountNoiseDecoratorConfigBuilder
import com.swordglowsblue.artifice.api.builder.data.worldgen.configured.decorator.config.CarvingMaskDecoratorConfigBuilder
import net.minecraft.world.gen.GenerationStep
import com.swordglowsblue.artifice.api.builder.data.worldgen.configured.decorator.config.DepthAverageDecoratorConfigBuilder
import com.swordglowsblue.artifice.api.builder.data.worldgen.configured.decorator.config.CountNoiseBiasedDecoratorConfigBuilder

class CountNoiseBiasedDecoratorConfigBuilder : DecoratorConfigBuilder() {
    fun noiseToCountRatio(noiseToCountRatio: Int): CountNoiseBiasedDecoratorConfigBuilder {
        this.root.addProperty("noise_to_count_ratio", noiseToCountRatio)
        return this
    }

    fun noiseFactor(noiseFactor: Double): CountNoiseBiasedDecoratorConfigBuilder {
        this.root.addProperty("noise_factor", noiseFactor)
        return this
    }

    fun noiseOffset(noiseOffset: Double): CountNoiseBiasedDecoratorConfigBuilder {
        this.root.addProperty("noise_offset", noiseOffset)
        return this
    }
}