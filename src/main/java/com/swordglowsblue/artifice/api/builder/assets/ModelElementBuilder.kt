package com.swordglowsblue.artifice.api.builder.assets

import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder
import com.swordglowsblue.artifice.api.util.Builder
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.util.Identifier
import net.minecraft.util.math.Direction
import net.minecraft.util.math.MathHelper
import java.util.function.Function

/**
 * Builder for an individual model element.
 * @see ModelBuilder
 */
@Environment(EnvType.CLIENT)
class ModelElementBuilder internal constructor() :
    TypedJsonBuilder<JsonObject>(JsonObject(), Function { j: JsonObject -> j }) {
    /**
     * Set the start point of this cuboid.
     * @param x The start point on the X axis. Clamped to between -16 and 32.
     * @param y The start point on the Y axis. Clamped to between -16 and 32.
     * @param z The start point on the Z axis. Clamped to between -16 and 32.
     * @return this
     */
    fun from(x: Float, y: Float, z: Float): ModelElementBuilder {
        root.add(
            "from", arrayOf(
                MathHelper.clamp(x, -16f, 32f),
                MathHelper.clamp(y, -16f, 32f),
                MathHelper.clamp(z, -16f, 32f)
            )
        )
        return this
    }

    /**
     * Set the end point of this cuboid.
     * @param x The end point on the X axis. Clamped to between -16 and 32.
     * @param y The end point on the Y axis. Clamped to between -16 and 32.
     * @param z The end point on the Z axis. Clamped to between -16 and 32.
     * @return this
     */
    fun to(x: Float, y: Float, z: Float): ModelElementBuilder {
        root.add(
            "to", arrayOf(
                MathHelper.clamp(x, -16f, 32f),
                MathHelper.clamp(y, -16f, 32f),
                MathHelper.clamp(z, -16f, 32f)
            )
        )
        return this
    }

    /**
     * Set the rotation of this cuboid.
     * @param settings A callback which will be passed a [Rotation].
     * @return this
     */
    fun rotation(settings: Builder<Rotation>): ModelElementBuilder {
        with("rotation", { JsonObject() }) { rotation: JsonObject ->
            Rotation(rotation).apply(settings).buildTo(rotation)
        }
        return this
    }

    /**
     * Set whether to render shadows on this cuboid.
     * @param shade Whether to shade (default: true).
     * @return this
     */
    fun shade(shade: Boolean): ModelElementBuilder {
        root.addProperty("shade", shade)
        return this
    }

    /**
     * Define properties of the face in the given direction.
     * @param side The direction of the face.
     * @param settings A callback which will be passed a [Face].
     * @return this
     */
    fun face(side: Direction, settings: Builder<Face>): ModelElementBuilder {
        with("faces", { JsonObject() }) { faces: JsonObject ->
            with(
                faces!!,
                side.getName(),
                { JsonObject() }) { face: JsonObject ->
                Face(face).apply(settings).buildTo(face)
            }
        }
        return this
    }

    /**
     * Builder for model element rotation.
     * @see ModelElementBuilder
     */
    @Environment(EnvType.CLIENT)
    class Rotation(root: JsonObject) : TypedJsonBuilder<JsonObject>(root, Function { j: JsonObject -> j }) {
        /**
         * Set the origin point of this rotation.
         * @param x The origin point on the X axis. Clamped to between -16 and 32.
         * @param y The origin point on the Y axis. Clamped to between -16 and 32.
         * @param z The origin point on the Z axis. Clamped to between -16 and 32.
         * @return this
         */
        fun origin(x: Float, y: Float, z: Float): Rotation {
            root.add(
                "origin", arrayOf(
                    MathHelper.clamp(x, -16f, 32f),
                    MathHelper.clamp(y, -16f, 32f),
                    MathHelper.clamp(z, -16f, 32f)
                )
            )
            return this
        }

        /**
         * Set the axis to rotate around.
         * @param axis The axis.
         * @return this
         */
        fun axis(axis: Direction.Axis): Rotation {
            root.addProperty("axis", axis.getName())
            return this
        }

        /**
         * Set the rotation angle in 22.5deg increments.
         * @param angle The angle.
         * @return this
         * @throws IllegalArgumentException if the angle is not between -45 and 45 or is not divisible by 22.5.
         */
        fun angle(angle: Float): Rotation {
            require(
                !(angle != MathHelper.clamp(
                    angle,
                    -45f,
                    45f
                ) || angle % 22.5f != 0f)
            ) { "Angle must be between -45 and 45 in increments of 22.5" }
            root.addProperty("angle", angle)
            return this
        }

        /**
         * Set whether to rescale this element's faces across the whole block.
         * @param rescale Whether to rescale (default: false).
         * @return this
         */
        fun rescale(rescale: Boolean): Rotation {
            root.addProperty("rescale", rescale)
            return this
        }
    }

    /**
     * Builder for a model element face.
     * @see ModelElementBuilder
     */
    @Environment(EnvType.CLIENT)
    class Face(root: JsonObject) : TypedJsonBuilder<JsonObject>(root, Function { j: JsonObject -> j }) {
        /**
         * Set the texture UV to apply to this face. Detected by position within the block if not specified.
         * @param x1 The start point on the X axis. Clamped to between 0 and 16.
         * @param x2 The end point on the X axis. Clamped to between 0 and 16.
         * @param y1 The start point on the Y axis. Clamped to between 0 and 16.
         * @param y2 The end point on the Y axis. Clamped to between 0 and 16.
         * @return this
         */
        fun uv(x1: Int, x2: Int, y1: Int, y2: Int): Face {
            root.add(
                "uv", arrayOf(
                    MathHelper.clamp(x1, 0, 16),
                    MathHelper.clamp(x2, 0, 16),
                    MathHelper.clamp(y1, 0, 16),
                    MathHelper.clamp(y2, 0, 16)
                )
            )
            return this
        }

        /**
         * Set the texture of this face to the given texture variable.
         * @param varName The variable name (e.g. `particle`).
         * @return this
         */
        fun texture(varName: String): Face {
            root.addProperty("texture", "#$varName")
            return this
        }

        /**
         * Set the texture of this face to the given texture id.
         * @param path The texture path (`namespace:type/textureid`).
         * @return this
         */
        fun texture(path: Identifier): Face {
            root.addProperty("texture", path.toString())
            return this
        }

        /**
         * Set the side of the block for which this face should be culled if touching another block.
         * @param side The side to cull on (defaults to the side specified for this face).
         * @return this
         */
        fun cullface(side: Direction): Face {
            root.addProperty("cullface", side.getName())
            return this
        }

        /**
         * Set the rotation of this face's texture in 90deg increments.
         * @param rotation The texture rotation.
         * @return this
         * @throws IllegalArgumentException if the rotation is not between 0 and 270 or is not divisible by 90.
         */
        fun rotation(rotation: Int): Face {
            require(
                !(rotation != MathHelper.clamp(
                    rotation,
                    0,
                    270
                ) || rotation % 90 != 0)
            ) { "Rotation must be between 0 and 270 in increments of 90" }
            root.addProperty("rotation", rotation)
            return this
        }

        /**
         * Set the tint index of this face. Used by color providers and only applicable for blocks with defined color providers (e.g. grass).
         * @param tintindex The tint index.
         * @return this
         */
        fun tintindex(tintindex: Int): Face {
            root.addProperty("tintindex", tintindex)
            return this
        }
    }
}