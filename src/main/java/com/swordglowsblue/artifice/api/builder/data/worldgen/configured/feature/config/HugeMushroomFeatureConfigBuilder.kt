package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.feature.config

import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.data.worldgen.BlockStateProviderBuilder

class HugeMushroomFeatureConfigBuilder : FeatureConfigBuilder() {
    fun <P : BlockStateProviderBuilder> capProvider(
        processor: P.() -> Unit,
        instance: P
    ): HugeMushroomFeatureConfigBuilder {
        with("cap_provider", { JsonObject() }) { jsonObject: JsonObject ->
            instance.apply(processor).buildTo(jsonObject)
        }
        return this
    }

    fun <P : BlockStateProviderBuilder> stemProvider(
        processor: P.() -> Unit,
        instance: P
    ): HugeMushroomFeatureConfigBuilder {
        with("stem_provider", { JsonObject() }) { jsonObject: JsonObject ->
            instance.apply(processor).buildTo(jsonObject)
        }
        return this
    }

    fun foliageRadius(foliageRadius: Int): HugeMushroomFeatureConfigBuilder {
        this.root.addProperty("foliage_radius", foliageRadius)
        return this
    }
}