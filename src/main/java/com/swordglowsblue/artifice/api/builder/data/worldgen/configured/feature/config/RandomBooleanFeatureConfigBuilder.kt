package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.feature.config

class RandomBooleanFeatureConfigBuilder : FeatureConfigBuilder() {
    fun featureOnTrue(configuredFeatureID: String): RandomBooleanFeatureConfigBuilder {
        this.root.addProperty("feature_true", configuredFeatureID)
        return this
    }

    fun featureOnFalse(configuredFeatureID: String): RandomBooleanFeatureConfigBuilder {
        this.root.addProperty("feature_false", configuredFeatureID)
        return this
    }
}