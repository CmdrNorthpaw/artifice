package com.swordglowsblue.artifice.api.builder.data.recipe

import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder
import com.swordglowsblue.artifice.api.resource.JsonResource
import net.minecraft.util.Identifier
import java.util.function.Function

/**
 * Base builder for a recipe (`namespace:recipes/id.json`).
 * @param <T> this
 * @see [Minecraft Wiki](https://minecraft.gamepedia.com/Recipe.JSON_format)
</T> */
abstract class RecipeBuilder<T : RecipeBuilder<T>?>(
    type: Identifier?
) : TypedJsonBuilder<JsonResource<JsonObject?>?>(
        JsonObject(),
        Function<JsonObject, JsonResource<JsonObject?>?> { root: JsonObject -> JsonResource(root) }) {
    /**
     * Set the type of this recipe.
     * @param id The type [Identifier].
     * @return this
     */

    init {
        type?.let { root.addProperty("type", type.toString()) }
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