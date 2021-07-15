package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.decorator

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
import java.util.function.Function

class ConfiguredDecoratorBuilder : TypedJsonBuilder<JsonObject?>(JsonObject(), Function { j: JsonObject? -> j }) {
    fun name(decoratorID: String): ConfiguredDecoratorBuilder {
        this.root.addProperty("type", decoratorID)
        return this
    }

    fun <C : DecoratorConfigBuilder> config(instance: C, processor: C.() -> Unit): ConfiguredDecoratorBuilder {
        with("config", { JsonObject() }) { jsonObject: JsonObject? ->
            instance.apply(processor).buildTo(jsonObject)
        }
        return this
    }

    fun defaultConfig(): ConfiguredDecoratorBuilder {
        return config(DecoratorConfigBuilder()) {}
    }
}