package com.swordglowsblue.artifice.api.builder.data.worldgen.configured

import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder
import com.swordglowsblue.artifice.api.resource.JsonResource
import java.util.function.Function

class ConfiguredCarverBuilder : TypedJsonBuilder<JsonResource<JsonObject?>?>(
    JsonObject(),
    Function<JsonObject?, JsonResource<JsonObject?>?> { root: JsonObject? -> JsonResource(root) }) {
    /**
     * @param id ID of an existing carver.
     * @return this
     */
    fun name(id: String?): ConfiguredCarverBuilder {
        this.root.addProperty("type", id)
        return this
    }

    fun probability(probability: Float): ConfiguredCarverBuilder {
        try {
            if (probability > 1.0f) throw Throwable("Probability can't be higher than 1.0F! Found $probability")
            if (probability < 0.0f) throw Throwable("Probability can't be smaller than 0.0F! Found $probability")
            val jsonObject = JsonObject()
            jsonObject.addProperty("probability", probability)
            this.root.add("config", jsonObject)
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        }
        return this
    }
}