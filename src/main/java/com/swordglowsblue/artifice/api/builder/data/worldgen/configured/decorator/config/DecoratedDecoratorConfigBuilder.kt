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

class DecoratedDecoratorConfigBuilder : DecoratorConfigBuilder() {
    fun outerDecorator(processor: ConfiguredDecoratorBuilder.() -> Unit): DecoratedDecoratorConfigBuilder {
        with("outer", { JsonObject() }) { jsonObject: JsonObject? ->
            ConfiguredDecoratorBuilder().apply(processor).buildTo(jsonObject)
        }
        return this
    }

    fun innerDecorator(processor: ConfiguredDecoratorBuilder.() -> Unit): DecoratedDecoratorConfigBuilder {
        with("inner", { JsonObject() }) { jsonObject: JsonObject? ->
            ConfiguredDecoratorBuilder().apply(processor).buildTo(jsonObject)
        }
        return this
    }
}