package com.swordglowsblue.artifice.api.builder.data.recipe

import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.swordglowsblue.artifice.api.builder.JsonObjectBuilder
import net.minecraft.util.Identifier

/**
 * Builder for a smithing recipe (`namespace:recipes/id.json`).
 * @see [Minecraft Wiki](https://minecraft.gamepedia.com/Recipe.JSON_format)
 */
class SmithingRecipeBuilder : RecipeBuilder<SmithingRecipeBuilder>(Identifier("smithing")) {
    /**
     * Set the item being smithed
     * @param id The item [Identifier]
     * @return this
     */
    fun baseItem(id: Identifier): SmithingRecipeBuilder {
        root.add("base", item(id))
        return this
    }

    /**
     * Set the item being smithed to be any one of the given tag
     * @param id The tag [Identifier]
     * @return this
     */
    fun baseTag(id: Identifier): SmithingRecipeBuilder {
        root.add("base", tag(id))
        return this
    }

    /**
     * Set the item being smithed as one of a list of options.
     * @param settings A callback which will be passed a [MultiIngredientBuilder].
     * @return this
     */
    fun multiBase(settings: MultiIngredientBuilder.() -> Unit): SmithingRecipeBuilder {
        root.add("base", MultiIngredientBuilder().apply(settings).build())
        return this
    }

    /**
     * Set the item to be added on during the smithing
     * @param id The item [Identifier]
     * @return this
     */
    fun additionItem(id: Identifier): SmithingRecipeBuilder {
        root.add("addition", item(id))
        return this
    }

    /**
     * Set the item to be added on to be any one of the given tag
     * @param id The ta [Identifier]
     * @return this
     */
    fun additionTag(id: Identifier): SmithingRecipeBuilder {
        root.add("addition", tag(id))
        return this
    }

    /**
     * Set the item being added on as one of a list of options.
     * @param settings A callback which will be passed a [MultiIngredientBuilder].
     * @return this
     */
    fun multiAddition(settings: MultiIngredientBuilder.() -> Unit): SmithingRecipeBuilder {
        root.add("addition", MultiIngredientBuilder().apply(settings).build())
        return this
    }

    /**
     * Set the result of the smithing.
     * Item NBT will be preserved.
     * @param id The [Identifier] of the resulting item
     * @return this
     */
    fun result(id: Identifier): SmithingRecipeBuilder {
        root.add("result", JsonPrimitive(id.toString()))
        return this
    }

    private fun item(id: Identifier): JsonObject {
        return JsonObjectBuilder().add("item", id.toString()).build()
    }

    private fun tag(id: Identifier): JsonObject {
        return JsonObjectBuilder().add("tag", id.toString()).build()
    }
}