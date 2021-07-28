package com.swordglowsblue.artifice.api.builder.assets

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.JsonObjectBuilder
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder
import com.swordglowsblue.artifice.api.resource.JsonResource
import com.swordglowsblue.artifice.api.util.Builder
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import java.util.function.Function

/**
 * Builder for a texture animation file (`namespace:textures/block|item/textureid.mcmeta`).
 * @see [Minecraft Wiki](https://minecraft.gamepedia.com/Resource_pack.Animation)
 */
@Environment(EnvType.CLIENT)
class AnimationBuilder : TypedJsonBuilder<JsonResource<JsonObject>>(
    JsonObject(),
    { animation ->
        JsonResource(
            JsonObjectBuilder().add(
                "animation",
                animation
            ).build()
        )
    }) {
    /**
     * Set whether this animation should interpolate between frames with a frametime &gt; 1 between them.
     * @param interpolate Whether to interpolate (default: false).
     * @return this
     */
    fun interpolate(interpolate: Boolean): AnimationBuilder {
        root.addProperty("interpolate", interpolate)
        return this
    }

    /**
     * Set the frame width of this animation as a ratio of its frame height.
     * @param width The width (default: unset).
     * @return this
     */
    fun width(width: Int): AnimationBuilder {
        root.addProperty("width", width)
        return this
    }

    /**
     * Set the frame height of this animation as a ratio of its total pixel height.
     * @param height The height (default: unset).
     * @return this
     */
    fun height(height: Int): AnimationBuilder {
        root.addProperty("height", height)
        return this
    }

    /**
     * Set the default time to spend on each frame.
     * @param frametime The number of ticks the frame should last (default: 1)
     * @return this
     */
    fun frametime(frametime: Int): AnimationBuilder {
        root.addProperty("frametime", frametime)
        return this
    }

    /**
     * Set the frame order and/or frame-specific timings of this animation.
     * @param settings A callback which will be passed a [FrameOrder].
     * @return this
     */
    fun frames(settings: Builder<FrameOrder>): AnimationBuilder {
        root.add("frames", FrameOrder().apply(settings).build())
        return this
    }

    /**
     * Builder for the `frames` property of a texture animation file.
     * @see AnimationBuilder
     */
    @Environment(EnvType.CLIENT)
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
        fun frame(index: Int): FrameOrder {
            frames.add(index)
            return this
        }

        /**
         * Add a single frame to the end of the order, with a modified frametime specified.
         * @param index The frame index (starting from 0 at the top of the texture).
         * @param frametime The number of ticks the frame should last.
         * @return this
         */
        fun frame(index: Int, frametime: Int): FrameOrder {
            frames.add(JsonObjectBuilder().add("index", index).add("time", frametime).build())
            return this
        }

        /**
         * Add a range of frame indexes to this animation.
         * @param start The first frame index to add (inclusive).
         * @param endExclusive The last frame index to add (exclusive).
         * @return this
         * @see FrameOrder.frame
         */
        fun frameRange(start: Int, endExclusive: Int): FrameOrder {
            for (i in start until endExclusive) frames.add(i)
            return this
        }
    }
}