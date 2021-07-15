package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.feature.config

import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.data.worldgen.BlockStateProviderBuilder

class BlockPileFeatureConfigBuilder : FeatureConfigBuilder() {
    fun <P : BlockStateProviderBuilder> stateProvider(
        processor: P.() -> Unit,
        instance: P
    ): BlockPileFeatureConfigBuilder {
        with("state_provider", { JsonObject() }) { jsonObject: JsonObject ->
            instance.apply(processor).buildTo(jsonObject)
        }
        return this
    }
}