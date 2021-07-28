package com.swordglowsblue.artifice.api.builder.assets

import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder
import com.swordglowsblue.artifice.api.resource.JsonResource
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import java.util.function.Function

/**
 * Builder for a translation file (`namespace:lang/language_code.json`).
 * @see [Minecraft Wiki](https://minecraft.gamepedia.com/Resource_pack.Language)
 */
@Environment(EnvType.CLIENT)
class TranslationBuilder : TypedJsonBuilder<JsonResource<JsonObject>>(
    JsonObject(),
    { root: JsonObject -> JsonResource(root) }) {
    /**
     * Add a translation entry.
     * @param key The translation key (e.g. `block.example.example_block`).
     * @param translation The translated string (e.g. `Example Block`).
     * @return this
     */
    fun entry(key: String, translation: String): TranslationBuilder {
        root.addProperty(key, translation)
        return this
    }
}