package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.decorator.config

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