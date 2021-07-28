package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.decorator.config

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