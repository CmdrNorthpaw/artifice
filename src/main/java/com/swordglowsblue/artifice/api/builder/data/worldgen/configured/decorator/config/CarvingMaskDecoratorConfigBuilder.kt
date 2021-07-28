package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.decorator.config

import net.minecraft.world.gen.GenerationStep

class CarvingMaskDecoratorConfigBuilder : DecoratorConfigBuilder() {
    fun probability(probability: Float): CarvingMaskDecoratorConfigBuilder {
        this.root.addProperty("probability", probability)
        return this
    }

    fun step(step: GenerationStep.Carver): CarvingMaskDecoratorConfigBuilder {
        this.root.addProperty("step", step.getName())
        return this
    }
}