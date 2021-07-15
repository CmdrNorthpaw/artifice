package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.feature.config

import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder
import java.util.function.Function

open class FeatureConfigBuilder : TypedJsonBuilder<JsonObject>(JsonObject(), Function { j: JsonObject -> j })