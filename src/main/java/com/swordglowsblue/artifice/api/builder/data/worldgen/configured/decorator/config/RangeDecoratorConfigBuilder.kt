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

class RangeDecoratorConfigBuilder : DecoratorConfigBuilder() {
    fun bottomOffset(bottomOffset: Int): RangeDecoratorConfigBuilder {
        this.root.addProperty("bottom_offset", bottomOffset)
        return this
    }

    fun topOffset(topOffset: Int): RangeDecoratorConfigBuilder {
        this.root.addProperty("top_offset", topOffset)
        return this
    }

    fun maximum(maximum: Int): RangeDecoratorConfigBuilder {
        this.root.addProperty("maximum", maximum)
        return this
    }
}