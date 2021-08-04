package com.swordglowsblue.artifice.api.builder.assets

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.JsonObjectBuilder
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder
import com.swordglowsblue.artifice.api.dsl.ArtificeDsl
import com.swordglowsblue.artifice.api.resource.JsonResource
import com.swordglowsblue.artifice.api.util.Builder
import com.swordglowsblue.artifice.api.util.addProperty
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import java.util.function.Function

/**
 * Builder for a texture animation file (`namespace:textures/block|item/textureid.mcmeta`).
 * @see [Minecraft Wiki](https://minecraft.gamepedia.com/Resource_pack.Animation)
 */
@Environment(EnvType.CLIENT)
@ArtificeDsl
class AnimationBuilder : TypedJsonBuilder<JsonResource<JsonObject>>(JsonObject(), { animation ->
        JsonResource(JsonObjectBuilder().add("animation", animation).build())
    }) {
    /**
     * Set whether this animation should interpolate between frames with a frametime &gt; 1 between them.
     * @param interpolate Whether to interpolate (default: false).
     * @return this
     */
    fun interpolate(interpolate: Boolean) = apply { this.interpolate = interpolate }

    var interpolate: Boolean?
    get() = root["interpolate"].asBoolean
    set(value) = root.addProperty("interpolate", value)

    /**
     * Set the frame width of this animation as a ratio of its frame height.
     * @param width The width (default: unset).
     * @return this
     */
    fun width(width: Int) = apply { this.width = width }

    var width: Int?
    get() = root["width"].asInt
    set(value) = root.addProperty("width", value)

    /**
     * Set the frame height of this animation as a ratio of its total pixel height.
     * @param height The height (default: unset).
     * @return this
     */
    fun height(height: Int) = apply { this.height = height }

    var height: Int?
    get() = root["height"].asInt
    set(value) = root.addProperty("height", value)

    /**
     * Set the default time to spend on each frame.
     * @param frametime The number of ticks the frame should last (default: 1)
     * @return this
     */
    fun frametime(frametime: Int): AnimationBuilder {
        root.addProperty("frametime", frametime)
        return this
    }

    var frametime: Int?
    get() = root["frametime"].asInt
    set(value) = root.addProperty("frametime", value)
    /**
     * Set the frame order and/or frame-specific timings of this animation.
     * @param settings A callback which will be passed a [FrameOrder].
     * @return this
     */
    fun frames(settings: Builder<FrameOrder>) = apply {
        root.add("frames", FrameOrder().apply(settings).build())
    }

    /**
     * Builder for the `frames` property of a texture animation file.
     * @see AnimationBuilder
     */
    @Environment(EnvType.CLIENT)
    @ArtificeDsl
    class FrameOrder {
        private val frames = JsonArray()

        fun build(): JsonArray {
            return frames
        }

        /**
         * Add a single frame to end of the order.
         * @param index The frame index (starting from 0 at the top of the texture).
         * @return this
         */
        fun addFrame(index: Int) = apply { frames.add(index) }

        /**
         * Add a single frame to the end of the order, with a modified frametime specified.
         * @param index The frame index (starting from 0 at the top of the texture).
         * @param frametime The number of ticks the frame should last.
         * @return this
         */
        fun addFrame(index: Int, frametime: Int) = apply {
            frames.add(JsonObjectBuilder().add("index", index).add("time", frametime).build())
        }

        /**
         * Add a range of frame indexes to this animation.
         * @param start The first frame index to add (inclusive).
         * @param endExclusive The last frame index to add (exclusive).
         * @return this
         * @see FrameOrder.frame
         */
        fun addFrameRange(start: Int, endExclusive: Int) = apply {
            for (i in start until endExclusive) frames.add(i)
        }
    }
}