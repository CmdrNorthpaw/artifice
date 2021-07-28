package com.swordglowsblue.artifice.api.builder.data.recipe

import com.swordglowsblue.artifice.api.builder.JsonObjectBuilder
import net.minecraft.util.Identifier

/**
 * Builder for a stonecutting recipe (`namespace:recipes/id.json`).
 * @see [Minecraft Wiki](https://minecraft.gamepedia.com/Recipe.JSON_format)
 */
class StonecuttingRecipeBuilder : RecipeBuilder<StonecuttingRecipeBuilder>(Identifier("stonecutting")) {
    /**
     * Set the item being cut.
     * @param id The item ID.
     * @return this
     */
    fun ingredientItem(id: Identifier): StonecuttingRecipeBuilder {
        root.add("ingredient", JsonObjectBuilder().add("item", id.toString()).build())
        return this
    }

    /**
     * Set the item being cut as any of the given tag.
     * @param id The tag ID.
     * @return this
     */
    fun ingredientTag(id: Identifier): StonecuttingRecipeBuilder {
        root.add("ingredient", JsonObjectBuilder().add("tag", id.toString()).build())
        return this
    }

    /**
     * Set the item being cut as one of a list of options.
     * @param settings A callback which will be passed a [MultiIngredientBuilder].
     * @return this
     */
    fun multiIngredient(settings: MultiIngredientBuilder.() -> Unit): StonecuttingRecipeBuilder {
        root.add("ingredient", MultiIngredientBuilder().apply(settings).build())
        return this
    }

    /**
     * Set the item produced by this recipe.
     * @param id The item ID.
     * @return this
     */
    fun result(id: Identifier): StonecuttingRecipeBuilder {
        root.addProperty("result", id.toString())
        return this
    }

    /**
     * Set the number of items produced by this recipe.
     * @param count The number of result items.
     * @return this
     */
    fun count(count: Int): StonecuttingRecipeBuilder {
        root.addProperty("count", count)
        return this
    }

}