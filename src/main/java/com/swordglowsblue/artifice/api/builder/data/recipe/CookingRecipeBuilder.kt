package com.swordglowsblue.artifice.api.builder.data.recipe

import com.swordglowsblue.artifice.api.builder.JsonObjectBuilder
import com.swordglowsblue.artifice.api.util.IdUtils.asItem
import com.swordglowsblue.artifice.api.util.IdUtils.id
import net.minecraft.item.Item
import net.minecraft.util.Identifier
import kotlin.jvm.Throws

/**
 * Builder for cooking recipes (`namespace:recipes/id.json`).
 * Used for all types of cooking (smelting, blasting, smoking, campfire_cooking).
 * @see [Minecraft Wiki](https://minecraft.gamepedia.com/Recipe.JSON_format)
 */
class CookingRecipeBuilder(private val type: CookingRecipeType) : RecipeBuilder<CookingRecipeBuilder>(type.type) {
    /**
     * Set the item being cooked.
     * @param id The item ID.
     * @return this
     */
    fun ingredientItem(id: Identifier): CookingRecipeBuilder {
        root.add("ingredient", JsonObjectBuilder().add("item", id.toString()).build())
        return this
    }

    @set:Throws(IllegalArgumentException::class)
    var ingredientItem: Item?
    get() = Identifier.tryParse(root["ingredient"].asJsonObject["item"].asString)?.asItem
    set(value) {
        requireNotNull(value)
        root.add("ingredient", JsonObjectBuilder().add("item", value.id.toString()).build())
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

    @set:Throws(IllegalArgumentException::class)
    var ingredientTag: Identifier?
    get() = Identifier.tryParse(root.getAsJsonObject("ingredient")["tag"].asString)
    set(value) {
        requireNotNull(value)

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

    var result: Item
    get() = Identifier.tryParse(root["result"].asString)!!.asItem!!
    set(value) = root.addProperty("result", value.id.toString())

    /**
     * Set the amount of experience given by this recipe.
     * @param exp The amount of experience.
     * @return this
     */
    fun experience(exp: Double): CookingRecipeBuilder {
        root.addProperty("experience", exp)
        return this
    }

    var exp: Double
    get() = root["experience"].asDouble
    set(value) = root.addProperty("experience", value)

    /**
     * Set how long this recipe should take to complete in ticks.
     * @param time The number of ticks.
     * @return this
     */
    fun cookingTime(time: Int): CookingRecipeBuilder {
        root.addProperty("cookingtime", time)
        return this
    }

    var cookingTime: Int
    get() = root["cookingtime"]?.asInt ?: type.defaultTime
    set(value) = root.addProperty("cookingtime", value)

    enum class CookingRecipeType(val type: Identifier, val defaultTime: Int) {
        SMELTING(Identifier("smelting"), 200),
        SMOKING(Identifier("smoking"), 100),
        CAMPFIRE(Identifier("campfire_cooking"), 100),
        BLASTING(Identifier("blasting"), 100);

        companion object {
            fun fromId(id: Identifier) = values().first { it.type == id }
        }
    }
}