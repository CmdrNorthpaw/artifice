package com.swordglowsblue.artifice.api.builder.data.recipe

import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.swordglowsblue.artifice.api.builder.JsonObjectBuilder
import com.swordglowsblue.artifice.api.dsl.ArtificeDsl
import com.swordglowsblue.artifice.api.dsl.Tag
import com.swordglowsblue.artifice.api.util.IdUtils.asId
import com.swordglowsblue.artifice.api.util.IdUtils.asItem
import com.swordglowsblue.artifice.api.util.IdUtils.id
import com.swordglowsblue.artifice.api.util.IdUtils.parseId
import net.minecraft.item.Item
import net.minecraft.util.Identifier

/**
 * Builder for a smithing recipe (`namespace:recipes/id.json`).
 * @see [Minecraft Wiki](https://minecraft.gamepedia.com/Recipe.JSON_format)
 */
@ArtificeDsl
class SmithingRecipeBuilder : RecipeBuilder<SmithingRecipeBuilder>(Identifier("smithing")) {
    /**
     * Set the item being smithed
     * @param id The item [Identifier]
     * @return this
     */
    fun baseItem(item: Item) = apply { baseItem = item }

    var baseItem: Item?
    get() = parseId(root["base"].asString)?.asItem
    set(value) = root.addItem("base", value)

    /**
     * Set the item being smithed to be any one of the given tag
     * @param id The tag [Identifier]
     * @return this
     */
    fun baseTag(tag: Tag) = apply { baseTag = tag }

    var baseTag: Tag?
    get() = tagOrNull(parseId(root["base"].asString))
    set(value) = root.addTag("base", value)

    /**
     * Set the item being smithed as one of a list of options.
     * @param settings A callback which will be passed a [MultiIngredientBuilder].
     * @return this
     */
    fun multipleBaseItems(settings: MultiIngredientBuilder.() -> Unit) = apply {
        root.add("base", MultiIngredientBuilder().apply(settings).build())
        return this
    }

    /**
     * Set the item to be added on during the smithing
     * @param id The item [Identifier]
     * @return this
     */
    fun additionItem(item: Item) = apply { additionItem = item }

    var additionItem: Item?
    get() = parseId(root["addition"].asString)?.asItem
    set(value) = root.addItem("addition", value)

    /**
     * Set the item to be added on to be any one of the given tag
     * @param id The ta [Identifier]
     * @return this
     */
    fun additionTag(tag: Tag) = apply { additionTag = tag }

    var additionTag: Tag?
    get() = tagOrNull(parseId(root["addition"].asString))
    set(value) = root.addTag("addition", value)

    /**
     * Set the item being added on as one of a list of options.
     * @param settings A callback which will be passed a [MultiIngredientBuilder].
     * @return this
     */
    fun multiAddition(settings: MultiIngredientBuilder.() -> Unit) = apply {
        root.add("addition", MultiIngredientBuilder().apply(settings).build())
    }

    /**
     * Set the result of the smithing.
     * Item NBT will be preserved.
     * @param id The [Identifier] of the resulting item
     * @return this
     */
    fun result(result: Item) {
    }

    var result: Item?
    get() = root["result"].asId?.asItem
    set(value) = root.addProperty("item", value?.id.toString())
}