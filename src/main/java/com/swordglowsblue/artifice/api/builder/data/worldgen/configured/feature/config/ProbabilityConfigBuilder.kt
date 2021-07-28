package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.feature.config

class ProbabilityConfigBuilder : FeatureConfigBuilder() {
    fun probability(probability: Float): ProbabilityConfigBuilder {
        require(probability <= 1.0f) { "probability can't be higher than 1.0F! Found $probability" }
        require(probability >= 0.0f) { "probability can't be smaller than 0.0F! Found $probability" }
        this.root.addProperty("probability", probability)
        return this
    }
}