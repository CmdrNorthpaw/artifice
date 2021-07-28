package com.swordglowsblue.artifice.api.builder.assets

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder
import com.swordglowsblue.artifice.api.resource.JsonResource
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.util.Identifier
import java.util.function.Function

/**
 * Builder for a particle definition (`namespace:particles/particleid.json`).
 */
@Environment(EnvType.CLIENT)
class ParticleBuilder : TypedJsonBuilder<JsonResource<JsonObject>>(
    JsonObject(),
    { root -> JsonResource(root) }) {
    /**
     * Add a texture to this particle.
     * Calling this multiple times will add to the list instead of overwriting.
     * @param id The texture ID (`namespace:textureid`).
     * @return this
     */
    fun texture(id: Identifier): ParticleBuilder {
        with("textures", { JsonArray() }) { textures: JsonArray -> textures.add(id.toString()) }
        return this
    }
}