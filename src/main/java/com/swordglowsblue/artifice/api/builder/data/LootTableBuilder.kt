package com.swordglowsblue.artifice.api.builder.data

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.JsonObjectBuilder
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder
import com.swordglowsblue.artifice.api.resource.JsonResource
import net.minecraft.util.Identifier
import java.util.function.Function

/**
 * Builder for loot table files (`namespace:loot_tables/type/lootid.json`).
 * @see [Minecraft Wiki](https://minecraft.gamepedia.com/Loot_table)
 */
class LootTableBuilder : TypedJsonBuilder<JsonResource<JsonObject>>(
    JsonObject(),
    { root: JsonObject -> JsonResource(root) }) {
    /**
     * Set the type of this loot table.
     * @param id The type ID.
     * @return this
     */
    fun type(id: Identifier): LootTableBuilder {
        root.addProperty("type", id.toString())
        return this
    }

    /**
     * Add a pool to this loot table.
     * @param settings A callback which will be passed a [Pool].
     * @return this
     */
    fun pool(settings: Pool.() -> Unit): LootTableBuilder {
        with("pools", { JsonArray() }) { pools: JsonArray -> pools.add(Pool().apply(settings).build()) }
        return this
    }

    /**
     * Builder for loot table pools.
     * @see LootTableBuilder
     */
    class Pool internal constructor() : TypedJsonBuilder<JsonObject>(JsonObject(), { j: JsonObject -> j }) {
        /**
         * Add an entry to this pool.
         * @param settings A callback which will be passed an [LootTableEntry].
         * @return this
         */
        fun entry(settings: LootTableEntry.() -> Unit): Pool {
            with("entries", { JsonArray() }) { entries: JsonArray -> entries.add(LootTableEntry().apply(settings).build()) }
            return this
        }

        /**
         * Add a condition to this pool. All conditions must pass for the pool to be used.
         * The specific properties of this vary by condition, so this falls through to direct JSON building.
         *
         * @param id The condition ID.
         * @param settings A callback which will be passed a [JsonObjectBuilder].
         * @return this
         * @see [Minecraft Wiki](https://minecraft.gamepedia.com/Loot_table.Conditions)
         */
        fun condition(id: Identifier, settings: JsonObjectBuilder.() -> Unit): Pool {
            with("conditions", { JsonArray() }) { conditions: JsonArray ->
                conditions.add(
                    JsonObjectBuilder().add("condition", id.toString())
                        .apply(settings)
                        .build()
                )
            }
            return this
        }

        /**
         * Set the number of rolls to apply this pool for.
         * @param rolls The number of rolls.
         * @return this
         */
        fun rolls(rolls: Int): Pool {
            root.addProperty("rolls", rolls)
            return this
        }

        /**
         * Set the number of rolls to apply this pool for as a range from which to randomly select a number.
         * @param min The minimum number of rolls (inclusive).
         * @param max The maximum number of rolls (inclusive).
         * @return this
         */
        fun rolls(min: Int, max: Int): Pool {
            root.add("rolls", JsonObjectBuilder().add("min", min).add("max", max).build())
            return this
        }

        /**
         * Set the number of bonus rolls to apply this pool for per point of luck.
         * @param rolls The number of rolls.
         * @return this
         */
        fun bonusRolls(rolls: Float): Pool {
            root.addProperty("bonus_rolls", rolls)
            return this
        }

        /**
         * Set the number of bonus rolls to apply this pool for per point of luck as a range from which to randomly select a number.
         * @param min The minimum number of rolls (inclusive).
         * @param max The maximum number of rolls (inclusive).
         * @return this
         */
        fun bonusRolls(min: Float, max: Float): Pool {
            root.add("bonus_rolls", JsonObjectBuilder().add("min", min).add("max", max).build())
            return this
        }

        /**
         * Builder for a loot table pool entry.
         * @see Pool
         */
    }
}

class LootTableEntry internal constructor() :
    TypedJsonBuilder<JsonObject>(JsonObject(), { j: JsonObject -> j }) {
    /**
     * Set the type of this entry.
     * @param id The type ID.
     * @return this
     */
    fun type(id: Identifier): LootTableEntry {
        root.addProperty("type", id.toString())
        return this
    }

    /**
     * Set the name of this entry's value. Expected value varies by type.
     * @param id The name of the value as an ID.
     * @return this
     */
    fun name(id: Identifier): LootTableEntry {
        root.addProperty("name", id.toString())
        return this
    }

    /**
     * Add a child to this entry.
     * @param settings A callback which will be passed an [LootTableEntry].
     * @return this
     */
    fun child(settings: LootTableEntry.() -> Unit): LootTableEntry {
        with("children", { JsonArray() }) { children: JsonArray ->
            children.add(
                LootTableEntry().apply(settings).build()
            )
        }
        return this
    }

    /**
     * For type `tag`, set whether to use the given tag as a list of equally weighted options or to use all tag entries.
     * @param expand Whether to expand.
     * @return this
     */
    fun expand(expand: Boolean): LootTableEntry {
        root.addProperty("expand", expand)
        return this
    }

    /**
     * Set the relative weight of this entry.
     * @param weight The weight.
     * @return this
     */
    fun weight(weight: Int): LootTableEntry {
        root.addProperty("weight", weight)
        return this
    }

    /**
     * Set the quality of this entry (modifies the weight based on the player's luck attribute).
     * @param quality The quality.
     * @return this
     */
    fun quality(quality: Int): LootTableEntry {
        root.addProperty("quality", quality)
        return this
    }

    /**
     * Add a function to be applied to this entry.
     * @param id The function ID.
     * @param settings A callback which will be passed a [Function].
     * @return this
     * @see [Minecraft Wiki](https://minecraft.gamepedia.com/Loot_table.Functions)
     */
    fun function(id: Identifier, settings: Function.() -> Unit): LootTableEntry {
        with("functions", { JsonArray() }) { functions: JsonArray ->
            val function = JsonObjectBuilder().add("function", id.toString()).build()
            functions.add(
                Function(function).apply(settings).build()
            )
        }
        return this
    }

    /**
     * Add a condition to this entry. All conditions must pass for the entry to be used.
     * The specific properties of this vary by condition, so this falls through to direct JSON building.
     *
     * @param id The condition ID.
     * @param settings A callback which will be passed a [JsonObjectBuilder].
     * @return this
     * @see [Minecraft Wiki](https://minecraft.gamepedia.com/Loot_table.Conditions)
     */
    fun condition(id: Identifier, settings: JsonObjectBuilder.() -> Unit): LootTableEntry {
        with("conditions", { JsonArray() }) { conditions: JsonArray ->
            conditions.add(
                JsonObjectBuilder().add("condition", id.toString())
                    .apply(settings)
                    .build()
            )
        }
        return this
    }

    /**
     * Builder for loot table entry functions.
     * @see LootTableEntry
     *
     * @see [Minecraft Wiki](https://minecraft.gamepedia.com/Loot_table.Functions)
     */
    class Function internal constructor(func: JsonObject) : JsonObjectBuilder(func) {
        /**
         * Add a condition to this function. All conditions must pass for the function to be applied.
         * The specific properties of this vary by condition, so this falls through to direct JSON building.
         *
         * @param id The condition ID.
         * @param settings A callback which will be passed a [JsonObjectBuilder].
         * @return this
         * @see [Minecraft Wiki](https://minecraft.gamepedia.com/Loot_table.Conditions)
         */
        fun condition(id: Identifier, settings: JsonObjectBuilder.() -> Unit): Function {
            with("conditions", { JsonArray() }) { conditions: JsonArray ->
                conditions.add(
                    JsonObjectBuilder().add("condition", id.toString())
                        .apply(settings)
                        .build()
                )
            }
            return this
        }
    }
}