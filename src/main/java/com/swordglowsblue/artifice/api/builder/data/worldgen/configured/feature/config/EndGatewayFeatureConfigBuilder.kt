package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.feature.config

import com.google.gson.JsonArray

class EndGatewayFeatureConfigBuilder : FeatureConfigBuilder() {
    fun exit(x: Int, y: Int, z: Int): EndGatewayFeatureConfigBuilder {
        this.root.add("exit", JsonArray())
        this.root.getAsJsonArray("exit").add(x)
        this.root.getAsJsonArray("exit").add(y)
        this.root.getAsJsonArray("exit").add(z)
        return this
    }

    fun exact(exact: Boolean): EndGatewayFeatureConfigBuilder {
        this.root.addProperty("exact", exact)
        return this
    }
}