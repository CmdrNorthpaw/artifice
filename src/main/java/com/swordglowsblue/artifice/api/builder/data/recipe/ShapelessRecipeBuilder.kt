package com.swordglowsblue.artifice.api.builder.data.recipe

import com.google.gson.JsonArray
import com.swordglowsblue.artifice.api.builder.JsonObjectBuilder
import com.swordglowsblue.artifice.api.dsl.ArtificeDsl
import com.swordglowsblue.artifice.api.dsl.Tag
import com.swordglowsblue.artifice.api.util.Builder
import net.minecraft.item.Item
import net.minecraft.util.Identifier

/**
 * Builder for a shapeless crafting recipe (`namespace:recipes/id.json`).
 * @see [Minecraft Wiki](https://minecraft.gamepedia.com/Recipe.JSON_format)
 */
@ArtificeDsl
class ShapelessRecipeBuilder : RecipeBuilder<ShapelessRecipeBuilder>(Identifier("crafting_shapeless")) {
    /**
     * Add an ingredient item.
     * @param id The item ID.
     * @return this
     */
    fun addItemIngredient(item: Item) = apply {
        with("ingredients", { JsonArray() }) { ingredients: JsonArray ->
            ingredients.add(itemObject(item))
        }
    }

    /**
     * Add an ingredient item as any of the given tag.
     * @param id The tag ID.
     * @return this
     */
    fun addTagIngredient(tag: Tag) = apply {
        with("ingredients", { JsonArray() }) { ingredients: JsonArray ->
            ingredients.add(tagObject(tag))
        }
    }

    /**
     * Add an ingredient item as one of a list of options.
     * @param settings A callback which will be passed a [MultiIngredientBuilder].
     * @return this
     */
    fun multiIngredient(settings: Builder<MultiIngredientBuilder>) = apply {
        with("ingredients", { JsonArray() }) { ingredients: JsonArray ->
            ingredients.add(MultiIngredientBuilder().apply(settings).build())
        }
    }

    /**
     * Set the item produced by this recipe.
     * @param id The item ID.
     * @param count The number of result items.
     * @return this
     */
    fun result(result: RecipeResult) = apply { this.result = result }

    var result: RecipeResult?
    get() = RecipeResult.from(root["result"].asJsonObject)
    set(value) = root.add("result", value?.jsonObject)
}