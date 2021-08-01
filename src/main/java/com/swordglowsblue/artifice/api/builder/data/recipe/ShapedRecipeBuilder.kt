package com.swordglowsblue.artifice.api.builder.data.recipe

import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.JsonObjectBuilder
import com.swordglowsblue.artifice.api.dsl.ArtificeDsl
import com.swordglowsblue.artifice.api.dsl.Tag
import com.swordglowsblue.artifice.api.util.Builder
import com.swordglowsblue.artifice.api.util.IdUtils.id
import net.minecraft.item.Item
import net.minecraft.util.Identifier

/**
 * Builder for a shaped crafting recipe (`namespace:recipes/id.json`).
 * @see [Minecraft Wiki](https://minecraft.gamepedia.com/Recipe.JSON_format)
 */
@ArtificeDsl
class ShapedRecipeBuilder : RecipeBuilder<ShapedRecipeBuilder>(Identifier("crafting_shaped")) {
    /**
     * Set the recipe pattern for this recipe.
     * Each character of the given strings should correspond to a key registered for an ingredient.
     * @param rows The individual rows of the pattern.
     * @return this
     */
    fun pattern(vararg rows: String) = apply {
        require(rows.size == 3) { "The pattern must have exactly 3 rows, found ${rows.size}" }
        rows.forEachIndexed { index, row ->
            require(row.length == 3) {
                "Pattern rows must have a length of at least 3. Pattern at $index had length ${row.length}"
            }
        }
        root.add("pattern", this.arrayOf(*rows))
    }

    /**
     * Add an ingredient item.
     * @param key The key in the recipe pattern corresponding to this ingredient.
     * @param id The item ID.
     * @return this
     */
    fun addItemIngredient(key: Char, item: Item) = apply {
        with("key", { JsonObject() }) { ingredients: JsonObject ->
            ingredients.add(key.toString(), itemObject(item))
        }
    }

    /**
     * Add an ingredient item as any of the given tag.
     * @param key The key in the recipe pattern corresponding to this ingredient.
     * @param id The tag ID.
     * @return this
     */
    fun addTagIngredient(key: Char, tag: Tag) = apply {
        with("key", { JsonObject() }) { ingredients: JsonObject ->
            ingredients.add(
                key.toString(),
                tagObject(tag)
            )
        }
    }

    /**
     * Add an ingredient item as one of a list of options.
     * @param key The key in the recipe pattern corresponding to this ingredient.
     * @param settings A callback which will be passed a [MultiIngredientBuilder].
     * @return this
     */
    fun addMultiIngredient(key: Char, settings: Builder<MultiIngredientBuilder>) = apply {
        with("key", { JsonObject() }) { ingredients: JsonObject ->
            ingredients.add(
                key.toString(),
                MultiIngredientBuilder().apply(settings).build()
            )
        }
    }

    /**
     * Set the item produced by this recipe.
     * @param id The item ID.
     * @param count The number of result items.
     * @return this
     */
    @JvmOverloads
    fun setResult(item: Item, count: Int = 1) = apply {
        root.add("result", JsonObjectBuilder()
            .add("item", item.id.toString())
            .add("count", count)
            .build())
    }
}