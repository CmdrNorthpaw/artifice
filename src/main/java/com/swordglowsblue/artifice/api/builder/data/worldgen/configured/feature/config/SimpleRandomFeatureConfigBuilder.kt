package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.feature.config

import com.google.gson.JsonArray

class SimpleRandomFeatureConfigBuilder : FeatureConfigBuilder() {
    fun addConfiguredFeature(configuredFeatureID: String?): SimpleRandomFeatureConfigBuilder {
        this.root.getAsJsonArray("features").add(configuredFeatureID)
        return this
    }

    init {
        this.root.add("features", JsonArray())
    }
}