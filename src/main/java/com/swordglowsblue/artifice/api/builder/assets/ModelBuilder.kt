package com.swordglowsblue.artifice.api.builder.assets

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder
import com.swordglowsblue.artifice.api.dsl.ArtificeDsl
import com.swordglowsblue.artifice.api.resource.JsonResource
import com.swordglowsblue.artifice.api.util.Builder
import com.swordglowsblue.artifice.api.util.IdUtils.asId
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.util.Identifier
import java.util.function.Function

/**
 * Builder for a model file (`namespace:models/block|item/modelid.json`).
 * @see [Minecraft Wiki](https://minecraft.gamepedia.com/Model)
 */
@Environment(EnvType.CLIENT)
@ArtificeDsl
class ModelBuilder : TypedJsonBuilder<JsonResource<JsonObject>>(
    JsonObject(),
    { root: JsonObject -> JsonResource(root) }) {
    /**
     * Set the parent model for this model to inherit from.
     * @param id The parent model ID (`namespace:block|item/modelid`
     * @return this
     */
    fun parent(id: Identifier) = apply {
        root.addProperty("parent", id.toString())
        return this
    }

    var parent: Identifier?
    get() = root["parent"].asId
    set(value) = root.addProperty("parent", value?.toString())

    /**
     * Associate a texture with the given variable name.
     * @param name The texture variable name.
     * @param path The texture ID (`namespace:type/textureid`).
     * @return this
     */
    fun texture(name: String, path: Identifier) = apply {
        with("textures", { JsonObject() }) { textures: JsonObject ->
            textures.addProperty(name, path.toString())
        }
    }

    /**
     * Modify the display transformation properties of this model for the given display position.
     * @param name The position name (e.g. `thirdperson_righthand`).
     * @param settings A callback which will be passed a [Display].
     * @return this
     */
    fun display(name: String, settings: Builder<Display>) = apply {
        with("display", { JsonObject() }) { display: JsonObject ->
            display.add(
                name, Display().apply(settings).buildTo(display)
            )
        }
    }

    /**
     * Add an element to this model.
     * @param settings A callback which will be passed a [ModelElementBuilder].
     * @return this
     */
    fun addElement(settings: Builder<ModelElementBuilder>) = apply {
        with("elements", { JsonArray() }) { elements: JsonArray ->
            elements.add(
                ModelElementBuilder().apply(settings).build()
            )
        }
    }

    /**
     * Set whether this model should use ambient occlusion for lighting. Only applicable for block models.
     * @param ambientocclusion Whether to use ambient occlusion.
     * @return this
     */
    fun ambientOcclusion(occlusion: Boolean) = apply { this.ambientOcclusion = occlusion }

    var ambientOcclusion: Boolean?
    get() = root["ambientocclusion"].asBoolean
    set(value) = root.addProperty("ambientocclusion", value)

    /**
     * Add a property override to this model. Only applicable for item models.
     * @param settings A callback which will be passed a [Override].
     * @return this
     */
    fun addOverride(settings: Builder<Override>) = apply {
        with("overrides", { JsonArray() }) { overrides: JsonArray ->
            overrides.add(
                Override().apply(settings).build()
            )
        }
    }

    /**
     * Builder for model display settings.
     * @see ModelBuilder
     */
    @Environment(EnvType.CLIENT)
    @ArtificeDsl
    class Display : TypedJsonBuilder<JsonObject>(JsonObject(), { j: JsonObject -> j }) {
        /**
         * Set the rotation of this model around each axis.
         * @param x The rotation around the X axis.
         * @param y The rotation around the Y axis.
         * @param z The rotation around the Z axis.
         * @return this
         */
        fun rotation(x: Float, y: Float, z: Float) = apply {
            root.add("rotation", this.arrayOf(x, y, z))
        }

        /**
         * Set the translation of this model along each axis.
         * @param x The translation along the X axis. Clamped to between -80 and 80.
         * @param y The translation along the Y axis. Clamped to between -80 and 80.
         * @param z The translation along the Z axis. Clamped to between -80 and 80.
         * @return this
         */
        fun translation(x: Float, y: Float, z: Float) = apply {
            root.add("translation", this.arrayOf(x, y, z))
        }

        /**
         * Set the scale of this model on each axis.
         * @param x The scale on the X axis. Clamped to &lt;= 4.
         * @param y The scale on the Y axis. Clamped to &lt;= 4.
         * @param z The scale on the Z axis. Clamped to &lt;= 4.
         * @return this
         */
        fun scale(x: Float, y: Float, z: Float) = apply {
            root.add("scale", this.arrayOf(x, y, z))
        }
    }

    /**
     * Builder for an item model property override.
     * @see ModelBuilder
     */
    @Environment(EnvType.CLIENT)
    @ArtificeDsl
    class Override : TypedJsonBuilder<JsonObject>(JsonObject(), { j: JsonObject -> j }) {
        /**
         * Set the required value of the given property.
         * Calling this multiple times will require all properties to match.
         * @param name The item property tag.
         * @param value The required integer value.
         * @return this
         * @see [Minecraft Wiki](https://minecraft.gamepedia.com/Model.Item_tags)
         */
        fun predicate(name: String, value: Int): Override {
            with("predicate", { JsonObject() }) { predicate: JsonObject ->
                predicate.addProperty(name, value)
            }
            return this
        }

        /**
         * Set the model to be used instead of this one if the predicate matches.
         * @param id The model id (`namespace:block|item/modelid`).
         * @return this
         */
        fun model(id: Identifier) = apply { this.model = id }

        var model: Identifier?
        get() = root["model"].asId
        set(value) = root.addProperty("model", value?.toString())
    }
}