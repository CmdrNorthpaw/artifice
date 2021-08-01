package com.swordglowsblue.artifice.api.builder.data.recipe

import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.JsonObjectBuilder
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder
import com.swordglowsblue.artifice.api.resource.JsonResource
import com.swordglowsblue.artifice.api.util.IdUtils.id
import net.minecraft.item.Item
import net.minecraft.util.Identifier
import java.util.function.Function

/**
 * Base builder for a recipe (`namespace:recipes/id.json`).
 * @param <T> this
 * @see [Minecraft Wiki](https://minecraft.gamepedia.com/Recipe.JSON_format)
</T> */
abstract class RecipeBuilder<T : RecipeBuilder<T>>(
    type: Identifier
) : TypedJsonBuilder<JsonResource<JsonObject>>(
        JsonObject(),
        { root: JsonObject -> JsonResource(root) }) {
    /**
     * Set the type of this recipe.
     * @param id The type [Identifier].
     * @return this
     */

    init {
        type.let { root.addProperty("type", type.toString()) }
    }

    protected fun JsonObject.addIngredient(key: String, item: Item) {
        val ingredientObject = JsonObjectBuilder().add("item", item.id.toString()).build()
        this.add(key, ingredientObject)
    }

    protected fun JsonObject.addIngredient(key: String, tag: Identifier) {
        val ingredientObject = JsonObjectBuilder().add("tag", tag.toString()).build()
        this.add(key, ingredientObject)
    }

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