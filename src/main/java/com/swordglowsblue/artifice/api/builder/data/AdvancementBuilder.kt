package com.swordglowsblue.artifice.api.builder.data

import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder
import com.swordglowsblue.artifice.api.resource.JsonResource
import com.google.gson.JsonObject
import com.google.gson.JsonArray
import kotlin.jvm.JvmOverloads
import com.swordglowsblue.artifice.api.builder.JsonObjectBuilder
import com.swordglowsblue.artifice.api.util.process
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import java.util.function.Function

/**
 * Builder for advancement files (`namespace:advancements/advid.json`).
 * @see [Minecraft Wiki](https://minecraft.gamepedia.com/Advancements.JSON_Format)
 */
class AdvancementBuilder : TypedJsonBuilder<JsonResource<JsonObject?>?>(
    JsonObject(),
    Function<JsonObject, JsonResource<JsonObject?>?> { root: JsonObject? -> JsonResource(root) }) {
    /**
     * Set the display options for this advancement.
     * @param settings A callback which will be passed a [Display].
     * @return this
     */
    fun display(settings: Display.() -> Unit): AdvancementBuilder {
        with("display", { JsonObject() }) {
                displayObject: JsonObject? -> Display().process(settings).buildTo(displayObject)
        }
        return this
    }

    /**
     * Set the parent advancement for this to inherit from.
     * @param id The parent advancement ID (`namespace:advid`).
     * @return this
     */
    fun parent(id: Identifier): AdvancementBuilder {
        root.addProperty("parent", id.toString())
        return this
    }

    /**
     * Add a critera for this advancement to be received.
     * @param name The name of this criteria.
     * @param settings A callback which will be passed a [Criteria].
     * @return this
     */
    fun criteria(name: String?, settings: Criteria.() -> Unit): AdvancementBuilder {
        with("criteria", { JsonObject() }) { criteria: JsonObject? ->
            with(criteria, name, { JsonObject() }) { criterion: JsonObject? ->
                Criteria().process(settings)
                    .buildTo(criterion)
            }
        }
        return this
    }

    /**
     * Set which criteria are required to receive this advancement.
     * Passing multiple critera names will allow the advancement to be received if any of the given critera are completed.
     * Calling this multiple times will add a new set of requirements. Each set must have at least one contained criteria completed
     * to receive the advancement.
     * If this is not called, all criteria will be required by default.
     *
     * @param anyOf A list of criteria names, any of which can be completed to fulfill this requirement.
     * @return this
     */
    fun requirement(vararg anyOf: String?): AdvancementBuilder {
        with("requirements", { JsonArray() }) { requirements: JsonArray ->
            val array = JsonArray()
            for (name in anyOf) array.add(name)
            requirements.add(array)
        }
        return this
    }

    /**
     * Builder for advancement display properties.
     * @see AdvancementBuilder
     */
    class Display : TypedJsonBuilder<JsonObject?>(JsonObject(), Function { j: JsonObject? -> j }) {
        /**
         * Set the icon item of this advancement.
         * @param item The item ID.
         * @param nbt A string containing the JSON-serialized NBT of the item.
         * @return this
         */
        @JvmOverloads
        fun icon(item: Identifier, nbt: String? = null): Display {
            with("icon", { JsonObject() }) { icon: JsonObject ->
                icon.addProperty("item", item.toString())
                if (nbt != null) icon.addProperty("nbt", nbt)
            }
            return this
        }

        /**
         * Set the title of this advancement.
         * @param title The title.
         * @return this
         */
        fun title(title: String?): Display {
            root.addProperty("title", title)
            return this
        }

        /**
         * Set the title of this advancement.
         * @param title The title.
         * @return this
         */
        fun title(title: Text?): Display {
            root.add("title", Text.Serializer.toJsonTree(title))
            return this
        }

        /**
         * Set the frame type of this advancement.
         * @param frame The frame type.
         * @return this
         */
        fun frame(frame: Frame): Display {
            root.addProperty("frame", frame.type)
            return this
        }

        /**
         * Set the background texture of this advancement. Only applicable for root advancements.
         * @param id The texture path (`namespace:textures/gui/advancements/backgrounds/bgname.png`).
         * @return this
         */
        fun background(id: Identifier): Display {
            root.addProperty("background", id.toString())
            return this
        }

        /**
         * Set the description of this advancement.
         * @param desc The description.
         * @return this
         */
        fun description(desc: String?): Display {
            root.addProperty("description", desc)
            return this
        }

        /**
         * Set the description of this advancement.
         * @param desc The description.
         * @return this
         */
        fun description(desc: Text?): Display {
            root.add("description", Text.Serializer.toJsonTree(desc))
            return this
        }

        /**
         * Set whether this advancement should show a popup onscreen when received.
         * @param show Whether to show the toast.
         * @return this
         */
        fun showToast(show: Boolean): Display {
            root.addProperty("show_toast", show)
            return this
        }

        /**
         * Set whether achieving this advancement should post a message in chat.
         * @param announce Whether to announce.
         * @return this
         */
        fun announceToChat(announce: Boolean): Display {
            root.addProperty("announce_to_chat", announce)
            return this
        }

        /**
         * Set whether this advancement should be hidden from the advancement menu until received.
         * @param hidden Whether to hide.
         * @return this
         */
        fun hidden(hidden: Boolean): Display {
            root.addProperty("hidden", hidden)
            return this
        }

        /**
         * Options for [Display.frame].
         */
        enum class Frame(
            /** The name of this frame when outputted to JSON.  */
            val type: String
        ) {
            CHALLENGE("challenge"), GOAL("goal"), TASK("task");
        }
    }

    /**
     * Builder for advancement criteria.
     * @see AdvancementBuilder
     *
     * @see [Minecraft Wiki](https://minecraft.gamepedia.com/Advancements.List_of_triggers)
     */
    class Criteria : TypedJsonBuilder<JsonObject?>(JsonObject(), Function { j: JsonObject? -> j }) {
        /**
         * Set the trigger condition of this criteria.
         * @param id The trigger ID (`namespace:triggerid`).
         * @return this
         * @see [Minecraft Wiki](https://minecraft.gamepedia.com/Advancements.List_of_triggers)
         */
        fun trigger(id: Identifier): Criteria {
            root.addProperty("trigger", id.toString())
            return this
        }

        /**
         * Set the condition values for the given trigger.
         * These vary from trigger to trigger, so this falls through to direct JSON building.
         *
         * @param settings A callback which will be passed a [JsonObjectBuilder].
         * @return this
         * @see [Minecraft Wiki](https://minecraft.gamepedia.com/Advancements.List_of_triggers)
         */
        fun conditions(settings: JsonObjectBuilder.() -> Unit): Criteria {
            root.add("conditions", JsonObjectBuilder().process(settings).build())
            return this
        }
    }
}