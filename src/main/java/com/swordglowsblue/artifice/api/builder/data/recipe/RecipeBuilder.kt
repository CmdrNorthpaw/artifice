package com.swordglowsblue.artifice.api.builder.data.recipe

import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.JsonObjectBuilder
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder
import com.swordglowsblue.artifice.api.dsl.Tag
import com.swordglowsblue.artifice.api.resource.JsonResource
import com.swordglowsblue.artifice.api.util.IdUtils.id
import net.minecraft.item.Item
import net.minecraft.util.Identifier

/**
 * Base builder for a recipe (`namespace:recipes/id.json`).
 * @param <T> this
 * @see [Minecraft Wiki](https://minecraft.gamepedia.com/Recipe.JSON_format)
</T> */
abstract class RecipeBuilder<T : RecipeBuilder<T>>(
    type: Identifier,
    group: Identifier? = null
) : TypedJsonBuilder<JsonResource<JsonObject>>(JsonObject(), { JsonResource(it) }) {

    init {
        root.addProperty("type", type.toString())
        group?.let { root.addProperty("group", group.toString()) }
    }

    protected fun JsonObject.addItem(key: String, item: Item?) = this.add(key, itemObject(item))

    protected fun JsonObject.addTag(key: String, tag: Tag?) = this.add(key, tagObject(tag))

    protected fun itemObject(from: Item?) = JsonObjectBuilder().add("item", from?.id.toString()).build()

    protected fun tagObject(from: Tag?) = JsonObjectBuilder().add("item", from.toString()).build()

    protected fun tagOrNull(from: Identifier?): Tag? = if (from == null) null else Tag(from)

    /**
     * Set the recipe book group of this recipe.
     * @param id The group [Identifier].
     * @return this
     */
    fun group(id: Identifier): T {
        root.addProperty("group", id.toString())
        return this as T
    }
}