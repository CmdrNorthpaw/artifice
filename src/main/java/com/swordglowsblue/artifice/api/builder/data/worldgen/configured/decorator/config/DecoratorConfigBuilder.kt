package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.decorator.config

import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder
import java.util.function.Function

open class DecoratorConfigBuilder : TypedJsonBuilder<JsonObject>(JsonObject(), { j: JsonObject -> j })