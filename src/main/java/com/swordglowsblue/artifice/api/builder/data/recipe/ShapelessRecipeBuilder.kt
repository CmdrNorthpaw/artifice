package com.swordglowsblue.artifice.api.builder.data.recipe

import com.google.gson.JsonArray
import com.swordglowsblue.artifice.api.builder.JsonObjectBuilder
import com.swordglowsblue.artifice.api.util.IdUtils.id
import net.minecraft.item.Item
import net.minecraft.util.Identifier

/**
 * Builder for a shapeless crafting recipe (`namespace:recipes/id.json`).
 * @see [Minecraft Wiki](https://minecraft.gamepedia.com/Recipe.JSON_format)
 */
class ShapelessRecipeBuilder : RecipeBuilder<ShapelessRecipeBuilder>(Identifier("crafting_shapeless")) {
    /**
     * Add an ingredient item.
     * @param item The item ID.
     * @return this
     */
    fun addItemIngredient(item: Item): ShapelessRecipeBuilder {
        with("ingredients", { JsonArray() }) { ingredients: JsonArray ->
            ingredients.add(
                JsonObjectBuilder().add(
                    "item",
                    item.id.toString()
                ).build()
            )
        }
        return this
    }

    /**
     * Add an ingredient item as any of the given tag.
     * @param id The tag ID.
     * @return this
     */
    fun addTagIngredient(id: Identifier): ShapelessRecipeBuilder {
        with("ingredients", { JsonArray() }) { ingredients: JsonArray ->
            ingredients.add(
                JsonObjectBuilder().add(
                    "tag",
                    id.toString()
                ).build()
            )
        }
        return this
    }

    /**
     * Add an ingredient item as one of a list of options.
     * @param settings A callback which will be passed a [MultiIngredientBuilder].
     * @return this
     */
    fun multiIngredient(settings: MultiIngredientBuilder.() -> Unit): ShapelessRecipeBuilder {
        with("ingredients", { JsonArray() }) { ingredients: JsonArray ->
            ingredients.add(
                MultiIngredientBuilder().apply(settings).build()
            )
        }
        return this
    }

    /**
     * Set the item produced by this recipe.
     * @param item The item ID.
     * @param count The number of result items.
     * @return this
     */
    @JvmOverloads
    fun result(item: Item, count: Int = 1): ShapelessRecipeBuilder {
        root.add("result", JsonObjectBuilder()
            .add("item", item.id.toString())
            .add("count", count).build())
        return this
    }
}