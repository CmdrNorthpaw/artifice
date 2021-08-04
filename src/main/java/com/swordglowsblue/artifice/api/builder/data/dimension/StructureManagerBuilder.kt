package com.swordglowsblue.artifice.api.builder.data.dimension

import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder
import com.swordglowsblue.artifice.api.dsl.ArtificeDsl
import java.util.function.Function
import com.swordglowsblue.artifice.api.util.Builder
import com.swordglowsblue.artifice.api.util.requireRange
import net.minecraft.util.Identifier

@ArtificeDsl
class StructureManagerBuilder : TypedJsonBuilder<JsonObject>(JsonObject(), { j: JsonObject -> j }) {
    /**
     * Build stronghold settings.
     * @param strongholdSettingsBuilder
     * @return
     */
    fun strongholdSettings(strongholdSettingsBuilder: Builder<StrongholdSettingsBuilder>) = apply {
        with("stronghold", { JsonObject() }) { jsonObject: JsonObject ->
            StrongholdSettingsBuilder().apply(strongholdSettingsBuilder).buildTo(jsonObject)
        }
    }

    /**
     * Add structure.
     * @param structureId
     * @param structureConfigBuilder
     * @return
     */
    fun addStructure(structureId: Identifier, structureConfigBuilder: Builder<StructureConfigBuilder>) = apply {
        with(root["structures"].asJsonObject, structureId.toString(), { JsonObject() }) { jsonObject ->
            StructureConfigBuilder().apply(structureConfigBuilder).buildTo(jsonObject)
        }
    }

    @ArtificeDsl
    class StrongholdSettingsBuilder : TypedJsonBuilder<JsonObject>(JsonObject(), { j: JsonObject -> j }) {
        /**
         * @param distance
         * @return
         */
        fun distance(distance: Int) = apply { this.distance = distance }

        var distance: Int?
        get() = root["distance"].asInt
        set(value) = root.addProperty("distance", requireRange(value, 0, 1023, "Spread"))

        /**
         * @param spread
         * @return
         */
        fun spread(spread: Int) = apply { this.spread = spread }

        var spread: Int?
        get() = root["spread"].asInt
        set(value) = root.addProperty("spread", requireRange(value, 0, 1023, "Spread"))

        /**
         * Set the number of stronghold in the dimension.
         * @param count
         * @return
         */
        fun count(count: Int) = apply { this.count = count }

        var count: Int?
        get() = root["count"].asInt
        set(value) = root.addProperty("count", requireRange(value, 1, 4095, "Count"))
    }

    @ArtificeDsl
    class StructureConfigBuilder : TypedJsonBuilder<JsonObject>(JsonObject(), { j: JsonObject -> j }) {
        fun spacing(spacing: Int) = apply { this.spacing = spacing }

        var spacing: Int?
        get() = root["spacing"].asInt
        set(value) = root.addProperty("spacing", requireRange(value, 0, 4096, "Spacing"))

        fun separation(separation: Int) = apply { this.separation = separation }

        var separation: Int?
        get() = root["seperation"].asInt
        set(value) = root.addProperty("spacing", requireRange(value, 0, 4096, "Seperation"))

        fun salt(salt: Int) = apply { this.salt = salt }

        var salt: Int?
        get() = root["salt"].asInt
        set(value) {
            requireNotNull(value)
            require(value >= 0) { "Count can't be smaller than 0! Found $value" }
            this.root.addProperty("salt", salt)
        }
    }

    init {
        this.root.add("structures", JsonObject())
    }
}