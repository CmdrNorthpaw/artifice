package com.swordglowsblue.artifice.api.builder.data.recipe

import com.swordglowsblue.artifice.api.builder.JsonObjectBuilder
import net.minecraft.util.Identifier

/**
 * Builder for cooking recipes (`namespace:recipes/id.json`).
 * Used for all types of cooking (smelting, blasting, smoking, campfire_cooking).
 * @see [Minecraft Wiki](https://minecraft.gamepedia.com/Recipe.JSON_format)
 */
class CookingRecipeBuilder(type: CookingRecipeType) : RecipeBuilder<CookingRecipeBuilder>(type.type) {
    /**
     * Set the item being cooked.
     * @param id The item ID.
     * @return this
     */
    fun ingredientItem(id: Identifier): CookingRecipeBuilder {
        root.add("ingredient", JsonObjectBuilder().add("item", id.toString()).build())
        return this
    }

    /**
     * Set the item being cooked as any of the given tag.
     * @param id The tag ID.
     * @return this
     */
    fun ingredientTag(id: Identifier): CookingRecipeBuilder {
        root.add("ingredient", JsonObjectBuilder().add("tag", id.toString()).build())
        return this
    }

    /**
     * Set the item being cooked as one of a list of options.
     * @param settings A callback which will be passed a [MultiIngredientBuilder].
     * @return this
     */
    fun multiIngredient(settings: MultiIngredientBuilder.() -> Unit): CookingRecipeBuilder {
        root.add("ingredient", MultiIngredientBuilder().apply(settings).build())
        return this
    }

    /**
     * Set the item produced by this recipe.
     * @param id The item ID.
     * @return this
     */
    fun result(id: Identifier): CookingRecipeBuilder {
        root.addProperty("result", id.toString())
        return this
    }

    /**
     * Set the amount of experience given by this recipe.
     * @param exp The amount of experience.
     * @return this
     */
    fun experience(exp: Double): CookingRecipeBuilder {
        root.addProperty("experience", exp)
        return this
    }

    /**
     * Set how long this recipe should take to complete in ticks.
     * @param time The number of ticks.
     * @return this
     */
    fun cookingTime(time: Int): CookingRecipeBuilder {
        root.addProperty("cookingtime", time)
        return this
    }

    enum class CookingRecipeType(val type: Identifier) {
        SMELTING(Identifier("smelting")),
        SMOKING(Identifier("smoking")),
        CAMPFIRE(Identifier("campfire_cooking")),
        BLASTING(Identifier("blasting"))
    }
}