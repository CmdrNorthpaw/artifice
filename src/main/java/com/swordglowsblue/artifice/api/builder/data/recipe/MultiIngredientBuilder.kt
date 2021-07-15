package com.swordglowsblue.artifice.api.builder.data.recipe

import com.google.gson.JsonArray
import com.swordglowsblue.artifice.api.builder.JsonObjectBuilder
import net.minecraft.util.Identifier

/**
 * Bulder for a recipe ingredient option list.
 * @see CookingRecipeBuilder
 *
 * @see ShapedRecipeBuilder
 *
 * @see ShapelessRecipeBuilder
 *
 * @see StonecuttingRecipeBuilder
 */
class MultiIngredientBuilder internal constructor() {
    private val ingredients = JsonArray()

    /**
     * Add an item as an option.
     * @param id The item ID.
     * @return this
     */
    fun item(id: Identifier): MultiIngredientBuilder {
        ingredients.add(JsonObjectBuilder().add("item", id.toString()).build())
        return this
    }

    /**
     * Add all items from the given tag as options.
     * @param id The tag ID.
     * @return this
     */
    fun tag(id: Identifier): MultiIngredientBuilder {
        ingredients.add(JsonObjectBuilder().add("tag", id.toString()).build())
        return this
    }

    fun build(): JsonArray {
        return ingredients
    }
}