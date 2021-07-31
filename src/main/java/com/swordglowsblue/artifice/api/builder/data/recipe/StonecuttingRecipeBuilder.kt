package com.swordglowsblue.artifice.api.builder.data.recipe

import com.swordglowsblue.artifice.api.builder.JsonObjectBuilder
import com.swordglowsblue.artifice.api.util.IdUtils.asItem
import com.swordglowsblue.artifice.api.util.IdUtils.id
import net.minecraft.item.Item
import net.minecraft.util.Identifier
import kotlin.jvm.Throws

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

    @set:Throws(IllegalArgumentException::class)
    var ingredientItem: Item?
    get() = Identifier.tryParse(root["ingredient"].asJsonObject["item"]?.asString)?.asItem
    set(value) {
        requireNotNull(value)
        root.add("ingredient", JsonObjectBuilder().add("item", value.id.toString()).build())
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

    var ingredientTag: Identifier?
    get() = Identifier.tryParse(root["ingredient"].asJsonObject["tag"]?.asString)
    set(value) {
        requireNotNull(value)
        ingredientTag(value)
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
    @JvmOverloads
    fun result(item: Item, count: Int = 1): StonecuttingRecipeBuilder {
        root.addProperty("result", item.id.toString())
        root.addProperty("count", count)
        return this
    }

}