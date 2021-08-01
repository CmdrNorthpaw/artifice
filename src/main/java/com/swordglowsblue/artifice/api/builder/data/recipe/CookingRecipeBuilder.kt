package com.swordglowsblue.artifice.api.builder.data.recipe

import com.swordglowsblue.artifice.api.dsl.ArtificeDsl
import com.swordglowsblue.artifice.api.util.Builder
import com.swordglowsblue.artifice.api.util.IdUtils.asItem
import com.swordglowsblue.artifice.api.util.IdUtils.parseId
import net.minecraft.item.Item
import net.minecraft.util.Identifier

/**
 * Builder for cooking recipes (`namespace:recipes/id.json`).
 * Used for all types of cooking (smelting, blasting, smoking, campfire_cooking).
 * @see [Minecraft Wiki](https://minecraft.gamepedia.com/Recipe.JSON_format)
 */
@ArtificeDsl
class CookingRecipeBuilder(private val type: CookingRecipeType) : RecipeBuilder<CookingRecipeBuilder>(type.type) {
    /**
     * Set the item being cooked.
     * @param item The item ID.
     * @return this
     */
    fun ingredientItem(item: Item) = apply { ingredientItem = item }

    var ingredientItem: Item?
    get() = parseId(root["ingredient"].asString)?.asItem
    set(value) = if (value != null) root.addItem("ingredient", value) else Unit

    /**
     * Set the item being cooked as any of the given tag.
     * @param id The tag ID.
     * @return this
     */
    fun ingredientTag(id: Identifier) = apply { ingredientTag = id }

    var ingredientTag: Identifier?
    get() = parseId(root["ingredient"].asString)
    set(value) { requireNotNull(value); root.addTag("ingredient", value) }

    /**
     * Set the item being cooked as one of a list of options.
     * @param settings A callback which will be passed a [MultiIngredientBuilder].
     * @return this
     */
    fun multiIngredient(settings: Builder<MultiIngredientBuilder>): CookingRecipeBuilder {
        root.add("ingredient", MultiIngredientBuilder().apply(settings).build())
        return this
    }

    /**
     * Set the item produced by this recipe.
     * @param item The item ID.
     * @return this
     */
    fun result(item: Item) = apply { result = item }

    var result: Item?
    get() = parseId(root["result"]?.asString)?.asItem
    set(value) = root.addItem("result", value)

    /**
     * Set the amount of experience given by this recipe.
     * @param exp The amount of experience.
     * @return this
     */
    fun experience(exp: Double): CookingRecipeBuilder {
        root.addProperty("experience", exp)
        return this
    }

    var experience: Double?
    get() = root["experience"]?.asDouble
    set(value) = root.addProperty("experience", value)

    /**
     * Set how long this recipe should take to complete in ticks.
     * @param time The number of ticks.
     * @return this
     */
    fun cookingTime(time: Int) = apply { cookingTime = time }

    var cookingTime: Int
    get() = root["cookingtime"]?.asInt ?: type.defaultCookingTime
    set(value) = root.addProperty("cookingtime", value)

    enum class CookingRecipeType(val type: Identifier, val defaultCookingTime: Int) {
        SMELTING(Identifier("smelting"), 200),
        SMOKING(Identifier("smoking"), 100),
        CAMPFIRE(Identifier("campfire_cooking"), 100),
        BLASTING(Identifier("blasting"), 100);
    }
}