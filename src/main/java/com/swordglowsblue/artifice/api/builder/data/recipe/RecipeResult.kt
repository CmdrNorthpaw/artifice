package com.swordglowsblue.artifice.api.builder.data.recipe

import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.JsonObjectBuilder
import com.swordglowsblue.artifice.api.dsl.ArtificeDsl
import com.swordglowsblue.artifice.api.util.IdUtils.asItem
import com.swordglowsblue.artifice.api.util.IdUtils.id
import com.swordglowsblue.artifice.api.util.IdUtils.parseId
import net.minecraft.item.Item

data class RecipeResult @JvmOverloads constructor(
    val item: Item,
    val count: Int = 1
) {
    val jsonObject: JsonObject = JsonObjectBuilder()
        .add("count", count)
        .add("item", item.id.toString())
        .build()
    companion object {
        fun from(json: JsonObject?): RecipeResult? {
            if (json?.get("item") == null || json.get("count") == null) return null

            return RecipeResult(parseId(json["item"].asString)?.asItem ?: return null, json["count"].asInt)
        }
    }

    @ArtificeDsl
    class Builder {
        var item: Item? = null
        var count: Int? = null

        fun build(): RecipeResult {
            val item = requireNotNull(item)
            val count = requireNotNull(count)
            return RecipeResult(item, count)
        }
    }
}