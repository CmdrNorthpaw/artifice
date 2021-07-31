package com.swordglowsblue.artifice.api.builder.data.recipe

import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.JsonObjectBuilder
import com.swordglowsblue.artifice.api.util.IdUtils.id
import net.minecraft.item.Item
import net.minecraft.util.Identifier

/**
 * Builder for a shaped crafting recipe (`namespace:recipes/id.json`).
 * @see [Minecraft Wiki](https://minecraft.gamepedia.com/Recipe.JSON_format)
 */
class ShapedRecipeBuilder : RecipeBuilder<ShapedRecipeBuilder>(Identifier("crafting_shaped")) {
    /**
     * Set the recipe pattern for this recipe.
     * Each character of the given strings should correspond to a key registered for an ingredient.
     * @param rows The individual rows of the pattern.
     * @return this
     */
    fun pattern(vararg rows: String): ShapedRecipeBuilder {
        root.add("pattern", this.arrayOf(*rows))
        return this
    }

    /**
     * Add an ingredient item.
     * @param key The key in the recipe pattern corresponding to this ingredient.
     * @param id The item ID.
     * @return this
     */
    fun addItemIngredient(key: Char, id: Identifier): ShapedRecipeBuilder {
        with("key", { JsonObject() }) { ingredients: JsonObject ->
            ingredients.add(
                key.toString(),
                JsonObjectBuilder().add("item", id.toString()).build()
            )
        }
        return this
    }

    /**
     * Add an ingredient item as any of the given tag.
     * @param key The key in the recipe pattern corresponding to this ingredient.
     * @param id The tag ID.
     * @return this
     */
    fun addTagIngredient(key: Char, id: Identifier): ShapedRecipeBuilder {
        with("key", { JsonObject() }) { ingredients: JsonObject ->
            ingredients.add(
                key.toString(),
                JsonObjectBuilder().add("tag", id.toString()).build()
            )
        }
        return this
    }

    /**
     * Add an ingredient item as one of a list of options.
     * @param key The key in the recipe pattern corresponding to this ingredient.
     * @param settings A callback which will be passed a [MultiIngredientBuilder].
     * @return this
     */
    fun multiIngredient(key: Char, settings: MultiIngredientBuilder.() -> Unit): ShapedRecipeBuilder {
        with("key", { JsonObject() }) { ingredients: JsonObject ->
            ingredients.add(
                key.toString(),
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
    fun result(item: Item, count: Int = 1): ShapedRecipeBuilder {
        root.add("result", JsonObjectBuilder()
            .add("item", item.id.toString())
            .add("count", count)
            .build())
        return this
    }
}