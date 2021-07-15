package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.feature.config

import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.data.StateDataBuilder

class HugeFungusFeatureConfigBuilder : FeatureConfigBuilder() {
    fun validBaseBlock(processor: StateDataBuilder.() -> Unit): HugeFungusFeatureConfigBuilder {
        with("valid_base_block", { JsonObject() }) { jsonObject: JsonObject ->
            StateDataBuilder().apply(processor).buildTo(jsonObject)
        }
        return this
    }

    fun stemState(processor: StateDataBuilder.() -> Unit): HugeFungusFeatureConfigBuilder {
        with("stem_state", { JsonObject() }) { jsonObject: JsonObject ->
            StateDataBuilder().apply(processor).buildTo(jsonObject)
        }
        return this
    }

    fun hatState(processor: StateDataBuilder.() -> Unit): HugeFungusFeatureConfigBuilder {
        with("hat_state", { JsonObject() }) { jsonObject: JsonObject ->
            StateDataBuilder().apply(processor).buildTo(jsonObject)
        }
        return this
    }

    fun decorState(processor: StateDataBuilder.() -> Unit): HugeFungusFeatureConfigBuilder {
        with("decor_state", { JsonObject() }) { jsonObject: JsonObject ->
            StateDataBuilder().apply(processor).buildTo(jsonObject)
        }
        return this
    }

    fun planted(planted: Boolean): HugeFungusFeatureConfigBuilder {
        this.root.addProperty("planted", planted)
        return this
    }
}