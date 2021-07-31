package com.swordglowsblue.artifice.api.builder.data.recipe

import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.swordglowsblue.artifice.api.builder.JsonObjectBuilder
import com.swordglowsblue.artifice.api.util.IdUtils.asItem
import com.swordglowsblue.artifice.api.util.IdUtils.id
import net.minecraft.item.Item
import net.minecraft.util.Identifier
import kotlin.jvm.Throws

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
    fun baseItem(item: Item): SmithingRecipeBuilder {
        root.add("base", item(item.id))
        return this
    }

    @set:Throws(IllegalArgumentException::class)
    var baseItem: Item?
    get() = Identifier.tryParse(root["base"].asJsonObject["item"].asString)?.asItem
    set(value) {
        requireNotNull(value)
        root.add("base", item(value.id))
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

    var baseTag: Identifier?
    get() = Identifier.tryParse(root["ingredient"].asJsonObject["tag"]?.asString)
    set(value) {
        requireNotNull(value)
        root.add("base", tag(value))
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

    var additionItem: Item?
        get() = Identifier.tryParse(root["base"].asJsonObject["item"].asString)?.asItem
        set(value) {
            requireNotNull(value)
            root.add("addition", item(value.id))
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

    @set:Throws(IllegalArgumentException::class)
    var additionTag: Identifier?
    get() = Identifier.tryParse(root["ingredient"].asJsonObject["tag"]?.asString)
    set(value) {
        requireNotNull(value)
        root.add("base", tag(value))
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
     * @param item The [Identifier] of the resulting item
     * @return this
     */
    fun result(item: Item): SmithingRecipeBuilder {
        root.add("result", JsonPrimitive(item.id.toString()))
        return this
    }

    var result: Item
    get() = Identifier.tryParse(root["result"].asString)!!.asItem!!
    set(value) = root.addProperty("result", value.id.toString())

    private fun item(id: Identifier): JsonObject {
        return JsonObjectBuilder().add("item", id.toString()).build()
    }

    private fun tag(id: Identifier): JsonObject {
        return JsonObjectBuilder().add("tag", id.toString()).build()
    }
}