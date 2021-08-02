package com.swordglowsblue.artifice.api.builder.data.recipe

import com.swordglowsblue.artifice.api.builder.JsonObjectBuilder
import com.swordglowsblue.artifice.api.dsl.ArtificeDsl
import com.swordglowsblue.artifice.api.dsl.Tag
import com.swordglowsblue.artifice.api.util.IdUtils.asId
import com.swordglowsblue.artifice.api.util.IdUtils.asItem
import com.swordglowsblue.artifice.api.util.IdUtils.id
import net.minecraft.item.Item
import net.minecraft.util.Identifier

/**
 * Builder for a stonecutting recipe (`namespace:recipes/id.json`).
 * @see [Minecraft Wiki](https://minecraft.gamepedia.com/Recipe.JSON_format)
 */
@ArtificeDsl
class StonecuttingRecipeBuilder : RecipeBuilder<StonecuttingRecipeBuilder>(Identifier("stonecutting")) {
    /**
     * Set the item being cut.
     * @param id The item ID.
     * @return this
     */
    fun itemIngredient(item: Item) = apply { itemIngredient = item }

    var itemIngredient: Item?
    get() = extractItem("ingredient")
    set(value) = root.addItem("ingredient", value)

    /**
     * Set the item being cut as any of the given tag.
     * @param id The tag ID.
     * @return this
     */
    fun tagIngredient(tag: Tag) = apply { tagIngredient = tag }

    var tagIngredient: Tag?
    get() = extractTag("ingredient")
    set(value) = root.addTag("ingredient", value)

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
    fun result(item: Item) = apply { result = item }

    var result: Item?
    get() = root["result"].asId?.asItem
    set(value) = root.addProperty("result", value?.id.toString())

    /**
     * Set the number of items produced by this recipe.
     * @param count The number of result items.
     * @return this
     */
    fun count(count: Int): StonecuttingRecipeBuilder {
        root.addProperty("count", count)
        return this
    }

    var count: Int?
    get() = root["count"]?.asInt
    set(value) = root.addProperty("count", value)

}