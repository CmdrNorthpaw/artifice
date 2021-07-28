package com.swordglowsblue.artifice.api.builder.data.dimension

import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder
import java.util.function.Function
import com.swordglowsblue.artifice.api.util.Builder

class StructureManagerBuilder : TypedJsonBuilder<JsonObject>(JsonObject(), Function { j: JsonObject -> j }) {
    /**
     * Build stronghold settings.
     * @param strongholdSettingsBuilder
     * @return
     */
    fun strongholdSettings(strongholdSettingsBuilder: Builder<StrongholdSettingsBuilder>): StructureManagerBuilder {
        with("stronghold", { JsonObject() }) { jsonObject: JsonObject ->
            StrongholdSettingsBuilder().apply(strongholdSettingsBuilder).buildTo(jsonObject)
        }
        return this
    }

    /**
     * Add structure.
     * @param structureId
     * @param structureConfigBuilder
     * @return
     */
    fun addStructure(
        structureId: String,
        structureConfigBuilder: Builder<StructureConfigBuilder>
    ): StructureManagerBuilder {
        this.with(this.root.getAsJsonObject("structures"), structureId, { JsonObject() }) { jsonObject ->
            StructureConfigBuilder().apply(structureConfigBuilder).buildTo(jsonObject)
        }
        return this
    }

    class StrongholdSettingsBuilder : TypedJsonBuilder<JsonObject>(JsonObject(), Function { j: JsonObject -> j }) {
        /**
         * @param distance
         * @return
         */
        fun distance(distance: Int): StrongholdSettingsBuilder {
            require(distance <= 1023) { "Distance can't be higher than 1023! Found $distance" }
            require(distance >= 0) { "Distance can't be smaller than 0! Found $distance" }
            this.root.addProperty("distance", distance)
            return this
        }

        /**
         * @param spread
         * @return
         */
        fun spread(spread: Int): StrongholdSettingsBuilder {
            require(spread <= 1023) { "Spread can't be higher than 1023! Found $spread" }
            require(spread >= 0) { "Spread can't be smaller than 0! Found $spread" }
            this.root.addProperty("spread", spread)
            return this
        }

        /**
         * Set the number of stronghold in the dimension.
         * @param count
         * @return
         */
        fun count(count: Int): StrongholdSettingsBuilder {
            require(count <= 4095) { "Count can't be higher than 4095! Found $count" }
            require(count >= 1) { "Count can't be smaller than 1! Found $count" }
            this.root.addProperty("count", count)
            return this
        }
    }

    class StructureConfigBuilder : TypedJsonBuilder<JsonObject>(JsonObject(), Function { j: JsonObject -> j }) {
        fun spacing(spacing: Int): StructureConfigBuilder {
            require(spacing <= 4096) { "Count can't be higher than 4096! Found $spacing" }
            require(spacing >= 0) { "Count can't be smaller than 0! Found $spacing" }
            this.root.addProperty("spacing", spacing)
            return this
        }

        fun separation(separation: Int): StructureConfigBuilder {
            require(separation <= 4096) { "Count can't be higher than 4096! Found $separation" }
            require(separation >= 0) { "Count can't be smaller than 0! Found $separation" }
            this.root.addProperty("separation", separation)
            return this
        }

        fun salt(salt: Int): StructureConfigBuilder {
            require(salt >= 0) { "Count can't be smaller than 0! Found $salt" }
            this.root.addProperty("salt", salt)
            return this
        }
    }

    init {
        this.root.add("structures", JsonObject())
    }
}