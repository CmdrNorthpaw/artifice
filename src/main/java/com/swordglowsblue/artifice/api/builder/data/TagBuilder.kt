package com.swordglowsblue.artifice.api.builder.data

import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder
import com.swordglowsblue.artifice.api.resource.JsonResource
import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.data.TagBuilder
import com.google.gson.JsonArray
import net.minecraft.util.Identifier
import java.util.function.Function

/**
 * Builder for tag files (`namespace:tags/type/tagid.json`).
 * @see [Minecraft Wiki](https://minecraft.gamepedia.com/Tag)
 */
class TagBuilder : TypedJsonBuilder<JsonResource<JsonObject?>?>(
    JsonObject(),
    Function<JsonObject, JsonResource<JsonObject?>?> { root: JsonObject? -> JsonResource(root) }) {
    /**
     * Set whether this tag should override or append to versions of the same tag in lower priority data packs.
     * @param replace Whether to replace.
     * @return this
     */
    fun replace(replace: Boolean): TagBuilder {
        root.addProperty("replace", replace)
        return this
    }

    /**
     * Add a value to this tag.
     * @param id The value ID.
     * @return this
     */
    fun value(id: Identifier): TagBuilder {
        with("values", { JsonArray() }) { values: JsonArray -> values.add(id.toString()) }
        return this
    }

    /**
     * Add multiple values to this tag.
     * @param ids The value IDs.
     * @return this
     */
    fun values(vararg ids: Identifier): TagBuilder {
        with("values", { JsonArray() }) { values: JsonArray -> for (id in ids) values.add(id.toString()) }
        return this
    }

    /**
     * Include another tag into this tag's values.
     * @param tagId The tag ID.
     * @return this
     */
    fun include(tagId: Identifier): TagBuilder {
        with("values", { JsonArray() }) { values: JsonArray -> values.add("#$tagId") }
        return this
    }
}