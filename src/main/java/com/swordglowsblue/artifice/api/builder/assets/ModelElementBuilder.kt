package com.swordglowsblue.artifice.api.builder.assets

import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder
import com.swordglowsblue.artifice.api.dsl.ArtificeDsl
import com.swordglowsblue.artifice.api.util.Builder
import com.swordglowsblue.artifice.api.util.IdUtils.asId
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
@ArtificeDsl
class ModelElementBuilder internal constructor() :
    TypedJsonBuilder<JsonObject>(JsonObject(), { j: JsonObject -> j }) {

    /**
     * Set the start point of this cuboid.
     * @param x The start point on the X axis. Clamped to between -16 and 32.
     * @param y The start point on the Y axis. Clamped to between -16 and 32.
     * @param z The start point on the Z axis. Clamped to between -16 and 32.
     * @return this
     */
    fun start(x: Float, y: Float, z: Float) = apply {
        root.add(
            "from", arrayOf(
                MathHelper.clamp(x, -16f, 32f),
                MathHelper.clamp(y, -16f, 32f),
                MathHelper.clamp(z, -16f, 32f)
            )
        )
    }

    /**
     * Set the end point of this cuboid.
     * @param x The end point on the X axis. Clamped to between -16 and 32.
     * @param y The end point on the Y axis. Clamped to between -16 and 32.
     * @param z The end point on the Z axis. Clamped to between -16 and 32.
     * @return this
     */
    fun end(x: Float, y: Float, z: Float) = apply {
        root.add(
            "to", arrayOf(
                MathHelper.clamp(x, -16f, 32f),
                MathHelper.clamp(y, -16f, 32f),
                MathHelper.clamp(z, -16f, 32f)
            )
        )
    }

    /**
     * Set the rotation of this cuboid.
     * @param settings A callback which will be passed a [Rotation].
     * @return this
     */
    fun rotation(settings: Builder<Rotation>) = apply {
        with("rotation", { JsonObject() }) { rotation: JsonObject ->
            Rotation(rotation).apply(settings).buildTo(rotation)
        }
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

    var shade: Boolean?
    get() = root["shade"].asBoolean
    set(value) = root.addProperty("shade", value)

    /**
     * Define properties of the face in the given direction.
     * @param side The direction of the face.
     * @param settings A callback which will be passed a [Face].
     * @return this
     */
    fun face(side: Direction, settings: Builder<Face>) = apply {
        with("faces", { JsonObject() }) { faces: JsonObject ->
            with(faces, side.getName(), { JsonObject() }) { face: JsonObject ->
                Face(face).apply(settings).buildTo(face)
            }
        }
    }

    /**
     * Builder for model element rotation.
     * @see ModelElementBuilder
     */
    @Environment(EnvType.CLIENT)
    @ArtificeDsl
    class Rotation(root: JsonObject) : TypedJsonBuilder<JsonObject>(root, { j: JsonObject -> j }) {
        /**
         * Set the origin point of this rotation.
         * @param x The origin point on the X axis. Clamped to between -16 and 32.
         * @param y The origin point on the Y axis. Clamped to between -16 and 32.
         * @param z The origin point on the Z axis. Clamped to between -16 and 32.
         * @return this
         */
        fun origin(x: Float, y: Float, z: Float) = apply {
            root.add(
                "origin", arrayOf(
                    MathHelper.clamp(x, -16f, 32f),
                    MathHelper.clamp(y, -16f, 32f),
                    MathHelper.clamp(z, -16f, 32f)
                )
            )
        }

        /**
         * Set the axis to rotate around.
         * @param axis The axis.
         * @return this
         */
        fun axis(axis: Direction.Axis) = apply { this.axis = axis }

        var axis: Direction.Axis?
        get() = Direction.Axis.fromName(root["axis"].asString)
        set(value) = root.addProperty("axis", value?.name)

        /**
         * Set the rotation angle in 22.5deg increments.
         * @param angle The angle.
         * @return this
         * @throws IllegalArgumentException if the angle is not between -45 and 45 or is not divisible by 22.5.
         */
        fun angle(angle: Float) = apply { this.angle = angle }

        var angle: Float?
        get() = root["angle"].asFloat
        set(value) {
            if (value == null) return
            require(!(value != MathHelper.clamp(value, -45f, 45f) || value % 22.5f != 0f)) {
                "Angle must be between -45 and 45 in increments of 22.5"
            }
            root.addProperty("angle", value)
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

        var rescale: Boolean?
        get() = root["rescale"].asBoolean
        set(value) = root.addProperty("rescale", value)
    }

    /**
     * Builder for a model element face.
     * @see ModelElementBuilder
     */
    @Environment(EnvType.CLIENT)
    @ArtificeDsl
    class Face(root: JsonObject) : TypedJsonBuilder<JsonObject>(root, { j: JsonObject -> j }) {
        /**
         * Set the texture UV to apply to this face. Detected by position within the block if not specified.
         * @param x1 The start point on the X axis. Clamped to between 0 and 16.
         * @param x2 The end point on the X axis. Clamped to between 0 and 16.
         * @param y1 The start point on the Y axis. Clamped to between 0 and 16.
         * @param y2 The end point on the Y axis. Clamped to between 0 and 16.
         * @return this
         */
        fun uv(x1: Int, x2: Int, y1: Int, y2: Int) = apply {
            root.add(
                "uv", arrayOf(
                    MathHelper.clamp(x1, 0, 16),
                    MathHelper.clamp(x2, 0, 16),
                    MathHelper.clamp(y1, 0, 16),
                    MathHelper.clamp(y2, 0, 16)
                )
            )
        }

        /**
         * Set the texture of this face to the given texture variable.
         * @param varName The variable name (e.g. `particle`).
         * @return this
         */
        fun texture(varName: String) = apply { this.textureVar = varName }

        var textureVar: String?
        get() = root["texture"].asString.removePrefix("#")
        set(value) = root.addProperty("texture", "#$value")

        var textureId: Identifier?
        get() = root["texture"].asId
        set(value) = root.addProperty("texture", value?.toString())

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
        fun faceToCull(side: Direction) = apply { this.faceToCull = side }

        var faceToCull: Direction?
        get() = Direction.byName(root["cullface"].asString)
        set(value) = root.addProperty("cullface", value?.name)

        /**
         * Set the rotation of this face's texture in 90deg increments.
         * @param rotation The texture rotation.
         * @return this
         * @throws IllegalArgumentException if the rotation is not between 0 and 270 or is not divisible by 90.
         */
        fun rotation(rotation: Int) = apply { this.rotation = rotation }

        var rotation: Int?
        get() = root["rotation"].asInt
        set(value) {
            if (value == null) { root.remove("rotation"); return }
            require(!(value != MathHelper.clamp(value, 0, 270) || value % 90 != 0)) {
                "Rotation must be between 0 and 270 in increments of 90"
            }
            root.addProperty("rotation", value)
        }

        /**
         * Set the tint index of this face. Used by color providers and only applicable for blocks with defined color providers (e.g. grass).
         * @param tintindex The tint index.
         * @return this
         */
        fun tintIndex(index: Int) = apply { this.tintIndex = index }

        var tintIndex: Int?
        get() = root["tintindex"].asInt
        set(value) = root.addProperty("tintindex", value)
    }
}