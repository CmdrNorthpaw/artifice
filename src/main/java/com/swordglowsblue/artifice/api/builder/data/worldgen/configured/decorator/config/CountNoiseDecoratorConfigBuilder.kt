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

class CountNoiseDecoratorConfigBuilder : DecoratorConfigBuilder() {
    fun noiseLevel(noiseLevel: Double): CountNoiseDecoratorConfigBuilder {
        this.root.addProperty("noise_level", noiseLevel)
        return this
    }

    fun belowNoise(belowNoise: Int): CountNoiseDecoratorConfigBuilder {
        this.root.addProperty("below_noise", belowNoise)
        return this
    }

    fun aboveNoise(aboveNoise: Int): CountNoiseDecoratorConfigBuilder {
        this.root.addProperty("above_noise", aboveNoise)
        return this
    }
}