package com.swordglowsblue.artifice.api.builder.data.recipe

import com.swordglowsblue.artifice.api.builder.data.recipe.RecipeBuilder
import com.swordglowsblue.artifice.api.builder.data.recipe.GenericRecipeBuilder
import com.google.gson.JsonElement
import com.swordglowsblue.artifice.api.builder.JsonObjectBuilder
import com.swordglowsblue.artifice.api.builder.JsonArrayBuilder
import com.swordglowsblue.artifice.api.util.Processor
import com.swordglowsblue.artifice.api.util.process
import net.minecraft.util.Identifier

/**
 * Builder for a recipe of an unknown type (`namespace:recipes/id.json`)
 * @see [Minecraft Wiki](https://minecraft.gamepedia.com/Recipe.JSON_format)
 */
class GenericRecipeBuilder(type: Identifier) : RecipeBuilder<GenericRecipeBuilder?>(type) {
    /**
     * Add a JSON element to this recipe.
     * @param name The key.
     * @param value The value.
     * @return this
     */
    fun add(name: String?, value: JsonElement?): RecipeBuilder<*> {
        root.add(name, value)
        return this
    }

    /**
     * Add a string to this recipe.
     * @param name The key.
     * @param value The value.
     * @return this
     */
    fun add(name: String?, value: String?): RecipeBuilder<*> {
        root.addProperty(name, value)
        return this
    }

    /**
     * Add a boolean to this recipe.
     * @param name The key.
     * @param value The value.
     * @return this
     */
    fun add(name: String?, value: Boolean): RecipeBuilder<*> {
        root.addProperty(name, value)
        return this
    }

    /**
     * Add a number to this recipe.
     * @param name The key.
     * @param value The value.
     * @return this
     */
    fun add(name: String?, value: Number?): RecipeBuilder<*> {
        root.addProperty(name, value)
        return this
    }

    /**
     * Add a character to this recipe.
     * @param name The key.
     * @param value The value.
     * @return this
     */
    fun add(name: String?, value: Char?): RecipeBuilder<*> {
        root.addProperty(name, value)
        return this
    }

    /**
     * Add a JSON object to this recipe.
     * @param name The key.
     * @param settings A callback which will be passed a [JsonObjectBuilder].
     * @return this
     */
    fun addObject(name: String?, settings: Processor<JsonObjectBuilder>): RecipeBuilder<*> {
        root.add(name, settings.process(JsonObjectBuilder()).build())
        return this
    }

    /**
     * Add a JSON array to this recipe.
     * @param name The key.
     * @param settings A callback which will be passed a [JsonArrayBuilder].
     * @return this
     */
    fun addArray(name: String?, settings: JsonArrayBuilder.() -> Unit): RecipeBuilder<*> {
        root.add(name, JsonArrayBuilder().apply(settings).build())
        return this
    }
}