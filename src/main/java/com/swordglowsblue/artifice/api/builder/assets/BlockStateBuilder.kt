package com.swordglowsblue.artifice.api.builder.assets

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.JsonObjectBuilder
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder
import com.swordglowsblue.artifice.api.resource.JsonResource
import com.swordglowsblue.artifice.api.util.Builder
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.util.Identifier
import java.util.function.Consumer
import java.util.function.Function

/**
 * Builder for a blockstate definition file (`namespace:blockstates/blockid.json`).
 * @see [Minecraft Wiki](https://minecraft.gamepedia.com/Model.Block_states)
 */
@Environment(EnvType.CLIENT)
class BlockStateBuilder : TypedJsonBuilder<JsonResource<JsonObject>>(
    JsonObject(),
    Function<JsonObject, JsonResource<JsonObject>> { root: JsonObject -> JsonResource(root) }) {
    /**
     * Add a variant for the given state key.
     * Calling this multiple times for the same key will modify the existing value.
     * `variant` and `multipart` are incompatible; calling this will remove any existing `multipart` definitions.
     *
     * @param name The state key (`""` for default or format: `"prop1=value,prop2=value"`).
     * @param settings A callback which will be passed a [Variant].
     * @return this
     */
    fun variant(name: String, settings: Builder<Variant>): BlockStateBuilder {
        root.remove("multipart")
        with("variants", { JsonObject() }) { variants: JsonObject ->
            with(variants, name, { JsonObject() }) { variant: JsonObject ->
                Variant(variant).apply(settings).buildTo(variant)
            }
        }
        return this
    }

    /**
     * Add a variant for the given state key, with multiple weighted random options.
     * Calling this multiple times for the same key will add to the list instead of overwriting.
     * `variant` and `multipart` are incompatible; calling this will remove any existing `multipart` definitions.
     *
     * @param name The state key (`""` for default or format: `"prop1=value,prop2=value"`).
     * @param settings A callback which will be passed a [Variant].
     * @return this
     */
    fun weightedVariant(name: String?, settings: Builder<Variant>): BlockStateBuilder {
        root.remove("multipart")
        with("variants", { JsonObject() }) { variants: JsonObject? ->
            with(variants!!, name!!, { JsonArray() }) { options: JsonArray ->
                options.add(
                    Variant().apply(settings).build()
                )
            }
        }
        return this
    }

    /**
     * Add a multipart case.
     * Calling this multiple times will add to the list instead of overwriting.
     * `variant` and `multipart` are incompatible; calling this will remove any existing `variant` definitions.
     *
     * @param settings A callback which will be passed a [Case].
     * @return this
     */
    fun multipartCase(settings: Builder<Case>): BlockStateBuilder {
        root.remove("variants")
        with("multipart", { JsonArray() }) { cases: JsonArray -> cases.add(Case().apply(settings).build()) }
        return this
    }

    /**
     * Builder for a blockstate variant definition.
     * @see BlockStateBuilder
     */
    @Environment(EnvType.CLIENT)
    class Variant : TypedJsonBuilder<JsonObject?> {
        constructor() : super(JsonObject(), Function<JsonObject, JsonObject?> { j: JsonObject? -> j }) {}
        constructor(root: JsonObject) : super(root, Function<JsonObject, JsonObject?> { j: JsonObject? -> j }) {}

        /**
         * Set the model this variant should use.
         * @param id The model ID (`namespace:block|item/modelid`).
         * @return this
         */
        fun model(id: Identifier): Variant {
            root.addProperty("model", id.toString())
            return this
        }

        /**
         * Set the rotation of this variant around the X axis in increments of 90deg.
         * @param x The X axis rotation.
         * @return this
         * @throws IllegalArgumentException if `x` is not divisible by 90.
         */
        fun rotationX(x: Int): Variant {
            require(x % 90 == 0) { "X rotation must be in increments of 90" }
            root.addProperty("x", x)
            return this
        }

        /**
         * Set the rotation of this variant around the Y axis in increments of 90deg.
         * @param y The Y axis rotation.
         * @return this
         * @throws IllegalArgumentException if `y` is not divisible by 90.
         */
        fun rotationY(y: Int): Variant {
            require(y % 90 == 0) { "Y rotation must be in increments of 90" }
            root.addProperty("y", y)
            return this
        }

        /**
         * Set whether the textures of this model should not rotate with it.
         * @param uvlock Whether to lock texture rotation or not.
         * @return this
         */
        fun uvlock(uvlock: Boolean): Variant {
            root.addProperty("uvlock", uvlock)
            return this
        }

        /**
         * Set the relative weight of this variant.
         * This property will be ignored if the variant is not added with [BlockStateBuilder.weightedVariant]
         * or [Case.weightedApply].
         * @param weight The weight.
         * @return this
         */
        fun weight(weight: Int): Variant {
            root.addProperty("weight", weight)
            return this
        }
    }

    /**
     * Builder for a blockstate multipart case.
     * @see BlockStateBuilder
     */
    @Environment(EnvType.CLIENT)
    class Case : TypedJsonBuilder<JsonObject?>(JsonObject(), Function { j: JsonObject? -> j }) {
        /**
         * Set the condition for this case to be applied.
         * Calling this multiple times with different keys will require all of the specified properties to match.
         * @param name The state name (e.g. `facing`).
         * @param state The state value (e.g. `north`).
         * @return this
         */
        fun `when`(name: String?, state: String?): Case {
            with("when", { JsonObject() }) { `when`: JsonObject ->
                `when`.remove("OR")
                `when`.addProperty(name, state)
            }
            return this
        }

        /**
         * Set the condition for this case to be applied.
         * Calling this multiple times with different keys will require at least one of the specified properties to match.
         * @param name The state name (e.g. `facing`).
         * @param state The state value (e.g. `north`).
         * @return this
         */
        fun whenAny(name: String?, state: String?): Case {
            with("when", { JsonObject() }) { `when`: JsonObject ->
                with(`when`, "OR", { JsonArray() }) { cases: JsonArray ->
                    `when`.entrySet().forEach(Consumer { (key) ->
                        if (key != "OR") `when`.remove(
                            key
                        )
                    })
                    cases.add(JsonObjectBuilder().add(name!!, state!!).build())
                }
            }
            return this
        }

        /**
         * Set the variant to be applied if the condition matches.
         * Calling this multiple times for the same key will overwrite the existing value.
         * @param settings A callback which will be passed a [Variant].
         * @return this
         */
        fun apply(settings: Builder<Variant>): Case {
            root.add("apply", Variant().apply(settings).build())
            return this
        }

        /**
         * Set the variant to be applied if the condition matches, with multiple weighted random options.
         * Calling this multiple times will add to the list instead of overwriting.
         * @param settings A callback which will be passed a [Variant].
         * @return this
         */
        fun weightedApply(settings: Builder<Variant>): Case {
            with("apply", { JsonArray() }) { options: JsonArray -> options.add(Variant().apply(settings).build()) }
            return this
        }
    }
}