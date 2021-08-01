package com.swordglowsblue.artifice.api.builder.data.recipe

import com.google.gson.JsonArray
import com.swordglowsblue.artifice.api.builder.JsonObjectBuilder
import com.swordglowsblue.artifice.api.dsl.ArtificeDsl
import com.swordglowsblue.artifice.api.util.IdUtils.id
import net.minecraft.item.Item
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
@ArtificeDsl
class MultiIngredientBuilder internal constructor() {
    private val ingredients = JsonArray()

    /**
     * Add an item as an option.
     * @param id The item ID.
     * @return this
     */
    fun addItemIngredient(item: Item) = apply {
        ingredients.add(JsonObjectBuilder().add("item", item.id.toString()).build())
    }

    /**
     * Add all items from the given tag as options.
     * @param id The tag ID.
     * @return this
     */
    fun addTagIngredient(id: Identifier) = apply {
        ingredients.add(JsonObjectBuilder().add("tag", id.toString()).build())
        return this
    }

    fun build() = ingredients
}